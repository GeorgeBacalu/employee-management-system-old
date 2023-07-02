package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_EXPERIENCES;
import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
            ExperienceDto experienceDto = experienceDtos.get(i);
            actions.andExpect(jsonPath("$[" + i + "].id").value(experienceDto.getId()));
            actions.andExpect(jsonPath("$[" + i + "].title").value(experienceDto.getTitle()));
            actions.andExpect(jsonPath("$[" + i + "].organization").value(experienceDto.getOrganization()));
            actions.andExpect(jsonPath("$[" + i + "].description").value(experienceDto.getDescription()));
            actions.andExpect(jsonPath("$[" + i + "].type").value(experienceDto.getType().toString()));
            actions.andExpect(jsonPath("$[" + i + "].startedAt").value(experienceDto.getStartedAt().toString()));
            actions.andExpect(jsonPath("$[" + i + "].finishedAt").value(experienceDto.getFinishedAt().toString()));
        }
        MvcResult result = actions.andReturn();
        List<ExperienceDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(experienceDtos);
    }

    @Test
    void findById_withValidId_shouldReturnExperienceWithGivenId() throws Exception {
        given(experienceService.findById(anyInt())).willReturn(experienceDto1);
        MvcResult result = mockMvc.perform(get(API_EXPERIENCES + "/{id}", VALID_ID))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(experienceDto1.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto1.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto1.getOrganization()))
              .andExpect(jsonPath("$.description").value(experienceDto1.getDescription()))
              .andExpect(jsonPath("$.type").value(experienceDto1.getType().toString()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto1.getFinishedAt().toString()))
              .andReturn();
        verify(experienceService).findById(VALID_ID);
        ExperienceDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto1);
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
        MvcResult result = mockMvc.perform(post(API_EXPERIENCES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(experienceDto1.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto1.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto1.getOrganization()))
              .andExpect(jsonPath("$.description").value(experienceDto1.getDescription()))
              .andExpect(jsonPath("$.type").value(experienceDto1.getType().toString()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto1.getFinishedAt().toString()))
              .andReturn();
        verify(experienceService).save(experienceDto1);
        ExperienceDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateExperienceWithGivenId() throws Exception {
        ExperienceDto experienceDto = experienceDto2; experienceDto.setId(VALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willReturn(experienceDto);
        MvcResult result = mockMvc.perform(put(API_EXPERIENCES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(experienceDto2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(experienceDto.getId()))
              .andExpect(jsonPath("$.title").value(experienceDto2.getTitle()))
              .andExpect(jsonPath("$.organization").value(experienceDto2.getOrganization()))
              .andExpect(jsonPath("$.description").value(experienceDto2.getDescription()))
              .andExpect(jsonPath("$.type").value(experienceDto2.getType().toString()))
              .andExpect(jsonPath("$.startedAt").value(experienceDto2.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(experienceDto2.getFinishedAt().toString()))
              .andReturn();
        verify(experienceService).updateById(experienceDto2, VALID_ID);
        ExperienceDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ExperienceDto.class);
        assertThat(response).isEqualTo(experienceDto);
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
}
