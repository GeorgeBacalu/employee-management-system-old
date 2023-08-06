package com.project.ems.integration.mentor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRestController;
import com.project.ems.mentor.MentorService;
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

import static com.project.ems.constants.EndpointConstants.API_MENTORS;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION;
import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToDtoList;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage1;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage2;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
@WebMvcTest(MentorRestController.class)
@ExtendWith(MockitoExtension.class)
class MentorRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MentorService mentorService;

    @Spy
    private ModelMapper modelMapper;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = convertToDto(modelMapper, getMockedMentor1());
        mentorDto2 = convertToDto(modelMapper, getMockedMentor2());
        mentorDtos = convertToDtoList(modelMapper, getMockedMentors());
    }

    @Test
    void findAll_shouldReturnListOfMentors() throws Exception {
        given(mentorService.findAll()).willReturn(mentorDtos);
        ResultActions actions = mockMvc.perform(get(API_MENTORS)).andExpect(status().isOk());
        for(int i = 0; i < mentorDtos.size(); i++) {
            assertMentorDto(actions, "$[" + i + "]", mentorDtos.get(i));
        }
        List<MentorDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(mentorDtos);
    }

    @Test
    void findById_withValidId_shouldReturnMentorWithGivenId() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        ResultActions actions = mockMvc.perform(get(API_MENTORS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertMentorDtoJson(actions, mentorDto1);
        MentorDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto1);
        verify(mentorService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_MENTORS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddMentorToList() throws Exception {
        given(mentorService.save(any(MentorDto.class))).willReturn(mentorDto1);
        ResultActions actions = mockMvc.perform(post(API_MENTORS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto1)))
              .andExpect(status().isCreated());
        assertMentorDtoJson(actions, mentorDto1);
        MentorDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto1);
        verify(mentorService).save(mentorDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateMentorWithGivenId() throws Exception {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willReturn(mentorDto);
        ResultActions actions = mockMvc.perform(put(API_MENTORS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto2)))
              .andExpect(status().isOk());
        verify(mentorService).updateById(mentorDto2, VALID_ID);
        assertMentorDtoJson(actions, mentorDto);
        MentorDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_MENTORS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).updateById(mentorDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_MENTORS + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(mentorService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(anyInt());
        mockMvc.perform(delete(API_MENTORS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).deleteById(INVALID_ID);
    }

    private Stream<Arguments> paginationArguments() {
        Page<MentorDto> mentorDtosPage1 = new PageImpl<>(convertToDtoList(modelMapper, getMockedMentorsPage1()));
        Page<MentorDto> mentorDtosPage2 = new PageImpl<>(convertToDtoList(modelMapper, getMockedMentorsPage2()));
        Page<MentorDto> mentorDtosPage3 = new PageImpl<>(convertToDtoList(modelMapper, getMockedMentorsPage3()));
        Page<MentorDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", MENTOR_FILTER_KEY, mentorDtosPage1),
                         Arguments.of(1, 2, "id", "asc", MENTOR_FILTER_KEY, mentorDtosPage2),
                         Arguments.of(2, 2, "id", "asc", MENTOR_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", mentorDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", mentorDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", mentorDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<MentorDto> expectedPage) throws Exception {
        given(mentorService.findAllByKey(any(Pageable.class), anyString())).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_MENTORS + API_PAGINATION, page, size, sortField, sortDirection, key)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(int i = 0; i < expectedPage.getContent().size(); i++) {
            assertMentorDto(actions, "$.content[" + i + "]", expectedPage.getContent().get(i));
        }
        PageWrapper<MentorDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response.getContent()).isEqualTo(expectedPage.getContent());
    }

    private void assertMentorDto(ResultActions actions, String prefix, MentorDto mentorDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(mentorDto.getId()))
              .andExpect(jsonPath(prefix + ".name").value(mentorDto.getName()))
              .andExpect(jsonPath(prefix + ".email").value(mentorDto.getEmail()))
              .andExpect(jsonPath(prefix + ".password").value(mentorDto.getPassword()))
              .andExpect(jsonPath(prefix + ".mobile").value(mentorDto.getMobile()))
              .andExpect(jsonPath(prefix + ".address").value(mentorDto.getAddress()))
              .andExpect(jsonPath(prefix + ".birthday").value(mentorDto.getBirthday().toString()))
              .andExpect(jsonPath(prefix + ".roleId").value(mentorDto.getRoleId()))
              .andExpect(jsonPath(prefix + ".employmentType").value(mentorDto.getEmploymentType().name()))
              .andExpect(jsonPath(prefix + ".position").value(mentorDto.getPosition().name()))
              .andExpect(jsonPath(prefix + ".grade").value(mentorDto.getGrade().name()))
              .andExpect(jsonPath(prefix + ".supervisingMentorId").value(mentorDto.getSupervisingMentorId()))
              .andExpect(jsonPath(prefix + ".nrTrainees").value(mentorDto.getNrTrainees()))
              .andExpect(jsonPath(prefix + ".maxTrainees").value(mentorDto.getMaxTrainees()))
              .andExpect(jsonPath(prefix + ".isTrainingOpen").value(mentorDto.getIsTrainingOpen()));
        for(int j = 0; j < mentorDto.getStudiesIds().size(); j++) {
            actions.andExpect(jsonPath(prefix + ".studiesIds[" + j + "]").value(mentorDto.getStudiesIds().get(j)));
        }
        for(int j = 0; j < mentorDto.getExperiencesIds().size(); j++) {
            actions.andExpect(jsonPath(prefix + ".experiencesIds[" + j + "]").value(mentorDto.getExperiencesIds().get(j)));
        }
    }

    private void assertMentorDtoJson(ResultActions actions, MentorDto mentorDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(mentorDto.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto.getAddress()))
              .andExpect(jsonPath("$.birthday").value(mentorDto.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(mentorDto.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(mentorDto.getEmploymentType().name()))
              .andExpect(jsonPath("$.position").value(mentorDto.getPosition().name()))
              .andExpect(jsonPath("$.grade").value(mentorDto.getGrade().name()))
              .andExpect(jsonPath("$.supervisingMentorId").value(mentorDto.getSupervisingMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(mentorDto.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(mentorDto.getExperiencesIds().toArray())))
              .andExpect(jsonPath("$.nrTrainees").value(mentorDto.getNrTrainees()))
              .andExpect(jsonPath("$.maxTrainees").value(mentorDto.getMaxTrainees()))
              .andExpect(jsonPath("$.isTrainingOpen").value(mentorDto.getIsTrainingOpen()));
    }
}
