package com.project.ems.integration.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
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

import static com.project.ems.constants.EndpointConstants.API_STUDIES;
import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
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
            StudyDto studyDto = studyDtos.get(i);
            actions.andExpect(jsonPath("$[" + i + "].id").value(studyDto.getId()));
            actions.andExpect(jsonPath("$[" + i + "].title").value(studyDto.getTitle()));
            actions.andExpect(jsonPath("$[" + i + "].institution").value(studyDto.getInstitution()));
            actions.andExpect(jsonPath("$[" + i + "].description").value(studyDto.getDescription()));
            actions.andExpect(jsonPath("$[" + i + "].type").value(studyDto.getType().toString()));
            actions.andExpect(jsonPath("$[" + i + "].startedAt").value(studyDto.getStartedAt().toString()));
            actions.andExpect(jsonPath("$[" + i + "].finishedAt").value(studyDto.getFinishedAt().toString()));
        }
        MvcResult result = actions.andReturn();
        List<StudyDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(studyDtos);
    }

    @Test
    void findById_withValidId_shouldReturnStudyWithGivenId() throws Exception {
        given(studyService.findById(anyInt())).willReturn(studyDto1);
        MvcResult result = mockMvc.perform(get(API_STUDIES + "/{id}", VALID_ID))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(studyDto1.getId()))
              .andExpect(jsonPath("$.title").value(studyDto1.getTitle()))
              .andExpect(jsonPath("$.institution").value(studyDto1.getInstitution()))
              .andExpect(jsonPath("$.description").value(studyDto1.getDescription()))
              .andExpect(jsonPath("$.type").value(studyDto1.getType().toString()))
              .andExpect(jsonPath("$.startedAt").value(studyDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(studyDto1.getFinishedAt().toString()))
              .andReturn();
        verify(studyService).findById(VALID_ID);
        StudyDto response = objectMapper.readValue(result.getResponse().getContentAsString(), StudyDto.class);
        assertThat(response).isEqualTo(studyDto1);
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
        MvcResult result = mockMvc.perform(post(API_STUDIES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(studyDto1.getId()))
              .andExpect(jsonPath("$.title").value(studyDto1.getTitle()))
              .andExpect(jsonPath("$.institution").value(studyDto1.getInstitution()))
              .andExpect(jsonPath("$.description").value(studyDto1.getDescription()))
              .andExpect(jsonPath("$.type").value(studyDto1.getType().toString()))
              .andExpect(jsonPath("$.startedAt").value(studyDto1.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(studyDto1.getFinishedAt().toString()))
              .andReturn();
        verify(studyService).save(studyDto1);
        StudyDto response = objectMapper.readValue(result.getResponse().getContentAsString(), StudyDto.class);
        assertThat(response).isEqualTo(studyDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateStudyWithGivenId() throws Exception {
        StudyDto studyDto = studyDto2; studyDto.setId(VALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willReturn(studyDto);
        MvcResult result = mockMvc.perform(put(API_STUDIES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(studyDto2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(studyDto.getId()))
              .andExpect(jsonPath("$.title").value(studyDto2.getTitle()))
              .andExpect(jsonPath("$.institution").value(studyDto2.getInstitution()))
              .andExpect(jsonPath("$.description").value(studyDto2.getDescription()))
              .andExpect(jsonPath("$.type").value(studyDto2.getType().toString()))
              .andExpect(jsonPath("$.startedAt").value(studyDto2.getStartedAt().toString()))
              .andExpect(jsonPath("$.finishedAt").value(studyDto2.getFinishedAt().toString()))
              .andReturn();
        verify(studyService).updateById(studyDto2, VALID_ID);
        StudyDto response = objectMapper.readValue(result.getResponse().getContentAsString(), StudyDto.class);
        assertThat(response).isEqualTo(studyDto);
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
}
