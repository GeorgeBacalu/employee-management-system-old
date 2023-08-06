package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
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

import static com.project.ems.constants.EndpointConstants.API_EXPERIENCES;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION;
import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage3;
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
@WebMvcTest(ExperienceRestController.class)
@ExtendWith(MockitoExtension.class)
class ExperienceRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExperienceService experienceService;

    @Spy
    private ModelMapper modelMapper;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experienceDto1 = convertToDto(modelMapper, getMockedExperience1());
        experienceDto2 = convertToDto(modelMapper, getMockedExperience2());
        experienceDtos = convertToDtoList(modelMapper, getMockedExperiences());
    }

    @Test
    void findAll_shouldReturnListOfExperiences() throws Exception {
        given(experienceService.findAll()).willReturn(experienceDtos);
        ResultActions actions = mockMvc.perform(get(API_EXPERIENCES)).andExpect(status().isOk());
        for(int i = 0; i < experienceDtos.size(); i++) {
            assertExperienceDto(actions, "$[" + i + "]", experienceDtos.get(i));
        }
        List<ExperienceDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(experienceDtos);
    }

    @Test
    void findById_withValidId_shouldReturnExperienceWithGivenId() throws Exception {
        given(experienceService.findById(anyInt())).willReturn(experienceDto1);
        ResultActions actions = mockMvc.perform(get(API_EXPERIENCES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertExperienceDtoJson(actions, experienceDto1);
        ExperienceDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto1);
        verify(experienceService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(any())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_EXPERIENCES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddExperienceToList() throws Exception {
        given(experienceService.save(any(ExperienceDto.class))).willReturn(experienceDto1);
        ResultActions actions = mockMvc.perform(post(API_EXPERIENCES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isCreated());
        assertExperienceDtoJson(actions, experienceDto1);
        ExperienceDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto1);
        verify(experienceService).save(experienceDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateExperienceWithGivenId() throws Exception {
        ExperienceDto experienceDto = experienceDto2; experienceDto.setId(VALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willReturn(experienceDto);
        ResultActions actions = mockMvc.perform(put(API_EXPERIENCES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto2)))
              .andExpect(status().isOk());
        assertExperienceDtoJson(actions, experienceDto);
        ExperienceDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto);
        verify(experienceService).updateById(experienceDto2, VALID_ID);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_EXPERIENCES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).updateById(experienceDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenFromList() throws Exception {
        mockMvc.perform(delete(API_EXPERIENCES + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(experienceService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(anyInt());
        mockMvc.perform(delete(API_EXPERIENCES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).deleteById(INVALID_ID);
    }

    private Stream<Arguments> paginationArguments() {
        Page<ExperienceDto> experienceDtosPage1 = new PageImpl<>(convertToDtoList(modelMapper, getMockedExperiencesPage1()));
        Page<ExperienceDto> experienceDtosPage2 = new PageImpl<>(convertToDtoList(modelMapper, getMockedExperiencesPage2()));
        Page<ExperienceDto> experienceDtosPage3 = new PageImpl<>(convertToDtoList(modelMapper, getMockedExperiencesPage3()));
        Page<ExperienceDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", EXPERIENCE_FILTER_KEY, experienceDtosPage1),
                         Arguments.of(1, 2, "id", "asc", EXPERIENCE_FILTER_KEY, experienceDtosPage2),
                         Arguments.of(2, 2, "id", "asc", EXPERIENCE_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", experienceDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", experienceDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", experienceDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<ExperienceDto> expectedPage) throws Exception {
        given(experienceService.findAllByKey(any(Pageable.class), anyString())).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_EXPERIENCES + API_PAGINATION, page, size, sortField, sortDirection, key)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(int i = 0; i < expectedPage.getContent().size(); i++) {
            assertExperienceDto(actions, "$.content[" + i + "]", expectedPage.getContent().get(i));
        }
        PageWrapper<ExperienceDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response.getContent()).isEqualTo(expectedPage.getContent());
    }

    private void assertExperienceDto(ResultActions actions, String prefix, ExperienceDto experienceDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(experienceDto.getId()))
              .andExpect(jsonPath(prefix + ".title").value(experienceDto.getTitle()))
              .andExpect(jsonPath(prefix + ".organization").value(experienceDto.getOrganization()))
              .andExpect(jsonPath(prefix + ".description").value(experienceDto.getDescription()))
              .andExpect(jsonPath(prefix + ".type").value(experienceDto.getType().name()))
              .andExpect(jsonPath(prefix + ".startedAt").value(experienceDto.getStartedAt().toString()))
              .andExpect(jsonPath(prefix + ".finishedAt").value(experienceDto.getFinishedAt().toString()));
    }

    private void assertExperienceDtoJson(ResultActions actions, ExperienceDto experienceDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(experienceDto.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto.getOrganization()))
              .andExpect(jsonPath("$.description").value(experienceDto.getDescription()))
              .andExpect(jsonPath("$.type").value(experienceDto.getType().name()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto.getFinishedAt().toString()));
    }
}
