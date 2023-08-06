package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_FEEDBACKS;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION;
import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(FeedbackRestController.class)
@ExtendWith(MockitoExtension.class)
class FeedbackRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedbackService feedbackService;

    @Spy
    private ModelMapper modelMapper;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedbackDto1 = convertToDto(modelMapper, getMockedFeedback1());
        feedbackDto2 = convertToDto(modelMapper, getMockedFeedback2());
        feedbackDtos = convertToDtoList(modelMapper, getMockedFeedbacks());
    }

    @Test
    void findAll_shouldReturnListOfFeedbacks() throws Exception {
        given(feedbackService.findAll()).willReturn(feedbackDtos);
        ResultActions actions = mockMvc.perform(get(API_FEEDBACKS)).andExpect(status().isOk());
        for(int i = 0; i < feedbackDtos.size(); i++) {
            assertFeedbackDto(actions, "$[" + i + "]", feedbackDtos.get(i));
        }
        List<FeedbackDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_withValidId_shouldReturnFeedbackWithGivenId() throws Exception {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto1);
        ResultActions actions = mockMvc.perform(get(API_FEEDBACKS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertFeedbackDtoJson(actions, feedbackDto1);
        FeedbackDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), FeedbackDto.class);
        assertThat(response).isEqualTo(feedbackDto1);
        verify(feedbackService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_FEEDBACKS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddFeedbackToList() throws Exception {
        given(feedbackService.save(any(FeedbackDto.class))).willReturn(feedbackDto1);
        ResultActions actions = mockMvc.perform(post(API_FEEDBACKS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto1)))
              .andExpect(status().isCreated());
        assertFeedbackDtoJson(actions, feedbackDto1);
        FeedbackDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), FeedbackDto.class);
        assertThat(response).isEqualTo(feedbackDto1);
        verify(feedbackService).save(feedbackDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateFeedbackWithGivenId() throws Exception {
        FeedbackDto feedbackDto = feedbackDto2; feedbackDto.setId(VALID_ID);
        given(feedbackService.updateById(any(FeedbackDto.class), anyInt())).willReturn(feedbackDto);
        ResultActions actions = mockMvc.perform(put(API_FEEDBACKS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto2)))
              .andExpect(status().isOk());
        assertFeedbackDtoJson(actions, feedbackDto);
        FeedbackDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), FeedbackDto.class);
        assertThat(response).isEqualTo(feedbackDto);
        verify(feedbackService).updateById(feedbackDto2, VALID_ID);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(any(FeedbackDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_FEEDBACKS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(feedbackDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).updateById(feedbackDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_FEEDBACKS + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(feedbackService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(anyInt());
        mockMvc.perform(delete(API_FEEDBACKS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).deleteById(INVALID_ID);
    }

    private Stream<Arguments> paginationArguments() {
        Page<FeedbackDto> feedbackDtosPage1 = new PageImpl<>(convertToDtoList(modelMapper, getMockedFeedbacksPage1()));
        Page<FeedbackDto> feedbackDtosPage2 = new PageImpl<>(convertToDtoList(modelMapper, getMockedFeedbacksPage2()));
        Page<FeedbackDto> feedbackDtosPage3 = new PageImpl<>(convertToDtoList(modelMapper, getMockedFeedbacksPage3()));
        Page<FeedbackDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", FEEDBACK_FILTER_KEY, feedbackDtosPage1),
                         Arguments.of(1, 2, "id", "asc", FEEDBACK_FILTER_KEY, feedbackDtosPage2),
                         Arguments.of(2, 2, "id", "asc", FEEDBACK_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", feedbackDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", feedbackDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", feedbackDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<FeedbackDto> expectedPage) throws Exception {
        given(feedbackService.findAllByKey(any(Pageable.class), anyString())).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_FEEDBACKS + API_PAGINATION, page, size, sortField, sortDirection, key)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(int i = 0; i < expectedPage.getContent().size(); i++) {
            assertFeedbackDto(actions, "$.content[" + i + "]", expectedPage.getContent().get(i));
        }
        PageWrapper<FeedbackDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response.getContent()).isEqualTo(expectedPage.getContent());
    }

    private void assertFeedbackDto(ResultActions actions, String prefix, FeedbackDto feedbackDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(feedbackDto.getId()))
              .andExpect(jsonPath(prefix + ".type").value(feedbackDto.getType().name()))
              .andExpect(jsonPath(prefix + ".description").value(feedbackDto.getDescription()))
              .andExpect(jsonPath(prefix + ".sentAt").value(feedbackDto.getSentAt() + ":00"))
              .andExpect(jsonPath(prefix + ".userId").value(feedbackDto.getUserId()));
    }

    private void assertFeedbackDtoJson(ResultActions actions, FeedbackDto feedbackDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(feedbackDto.getId()))
              .andExpect(jsonPath("$.type").value(feedbackDto.getType().name()))
              .andExpect(jsonPath("$.description").value(feedbackDto.getDescription()))
              .andExpect(jsonPath("$.sentAt").value(feedbackDto.getSentAt() + ":00"))
              .andExpect(jsonPath("$.userId").value(feedbackDto.getUserId()));
    }
}
