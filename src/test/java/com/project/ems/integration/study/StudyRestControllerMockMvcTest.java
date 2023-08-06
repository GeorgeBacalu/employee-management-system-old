package com.project.ems.integration.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
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

import static com.project.ems.constants.EndpointConstants.API_PAGINATION;
import static com.project.ems.constants.EndpointConstants.API_STUDIES;
import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage1;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage2;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage3;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
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
@WebMvcTest(StudyRestController.class)
@ExtendWith(MockitoExtension.class)
class StudyRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudyService studyService;

    @Spy
    private ModelMapper modelMapper;

    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        studyDto1 = convertToDto(modelMapper, getMockedStudy1());
        studyDto2 = convertToDto(modelMapper, getMockedStudy2());
        studyDtos = convertToDtoList(modelMapper, getMockedStudies());
    }

    @Test
    void findAll_shouldReturnListOfStudies() throws Exception {
        given(studyService.findAll()).willReturn(studyDtos);
        ResultActions actions = mockMvc.perform(get(API_STUDIES)).andExpect(status().isOk());
        for(int i = 0; i < studyDtos.size(); i++) {
            assertStudyDto(actions, "$[" + i + "]", studyDtos.get(i));
        }
        List<StudyDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(studyDtos);
    }

    @Test
    void findById_withValidId_shouldReturnStudyWithGivenId() throws Exception {
        given(studyService.findById(anyInt())).willReturn(studyDto1);
        ResultActions actions = mockMvc.perform(get(API_STUDIES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertStudyDtoJson(actions, studyDto1);
        StudyDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), StudyDto.class);
        assertThat(response).isEqualTo(studyDto1);
        verify(studyService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_STUDIES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddStudyToList() throws Exception {
        given(studyService.save(any(StudyDto.class))).willReturn(studyDto1);
        ResultActions actions = mockMvc.perform(post(API_STUDIES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto1)))
              .andExpect(status().isCreated());
        assertStudyDtoJson(actions, studyDto1);
        StudyDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), StudyDto.class);
        assertThat(response).isEqualTo(studyDto1);
        verify(studyService).save(studyDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateStudyWithGivenId() throws Exception {
        StudyDto studyDto = studyDto2; studyDto.setId(VALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willReturn(studyDto);
        ResultActions actions = mockMvc.perform(put(API_STUDIES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto2)))
              .andExpect(status().isOk());
        assertStudyDtoJson(actions, studyDto);
        StudyDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), StudyDto.class);
        assertThat(response).isEqualTo(studyDto);
        verify(studyService).updateById(studyDto2, VALID_ID);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_STUDIES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).updateById(studyDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveStudyWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_STUDIES + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(studyService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(anyInt());
        mockMvc.perform(delete(API_STUDIES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).deleteById(INVALID_ID);
    }

    private Stream<Arguments> paginationArguments() {
        Page<StudyDto> studyDtosPage1 = new PageImpl<>(convertToDtoList(modelMapper, getMockedStudiesPage1()));
        Page<StudyDto> studyDtosPage2 = new PageImpl<>(convertToDtoList(modelMapper, getMockedStudiesPage2()));
        Page<StudyDto> studyDtosPage3 = new PageImpl<>(convertToDtoList(modelMapper, getMockedStudiesPage3()));
        Page<StudyDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", STUDY_FILTER_KEY, studyDtosPage1),
                         Arguments.of(1, 2, "id", "asc", STUDY_FILTER_KEY, studyDtosPage2),
                         Arguments.of(2, 2, "id", "asc", STUDY_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", studyDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", studyDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", studyDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<StudyDto> expectedPage) throws Exception {
        given(studyService.findAllByKey(any(Pageable.class), anyString())).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_STUDIES + API_PAGINATION, page, size, sortField, sortDirection, key)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(int i = 0; i < expectedPage.getContent().size(); i++) {
            assertStudyDto(actions, "$.content[" + i + "]", expectedPage.getContent().get(i));
        }
        PageWrapper<StudyDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response.getContent()).isEqualTo(expectedPage.getContent());
    }

    private void assertStudyDto(ResultActions actions, String prefix, StudyDto studyDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(studyDto.getId()))
              .andExpect(jsonPath(prefix + ".title").value(studyDto.getTitle()))
              .andExpect(jsonPath(prefix + ".institution").value(studyDto.getInstitution()))
              .andExpect(jsonPath(prefix + ".description").value(studyDto.getDescription()))
              .andExpect(jsonPath(prefix + ".type").value(studyDto.getType().name()))
              .andExpect(jsonPath(prefix + ".startedAt").value(studyDto.getStartedAt().toString()))
              .andExpect(jsonPath(prefix + ".finishedAt").value(studyDto.getFinishedAt().toString()));
    }

    private void assertStudyDtoJson(ResultActions actions, StudyDto studyDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(studyDto.getId()))
              .andExpect(jsonPath("$.title").value(studyDto.getTitle()))
              .andExpect(jsonPath("$.institution").value(studyDto.getInstitution()))
              .andExpect(jsonPath("$.description").value(studyDto.getDescription()))
              .andExpect(jsonPath("$.type").value(studyDto.getType().name()))
              .andExpect(jsonPath("$.startedAt").value(studyDto.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(studyDto.getFinishedAt().toString()));
    }
}
