package com.project.ems.integration.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.Study;
import com.project.ems.study.StudyController;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.STUDIES;
import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_STUDY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDY_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
import static com.project.ems.mock.StudyMock.getMockedStudy3;
import static com.project.ems.mock.StudyMock.getMockedStudy4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudyController.class)
class StudyControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyService studyService;

    @MockBean
    private ModelMapper modelMapper;

    private Study study1;
    private Study study2;
    private Study study3;
    private Study study4;
    private List<Study> studies;
    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private StudyDto studyDto3;
    private StudyDto studyDto4;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        study1 = getMockedStudy1();
        study2 = getMockedStudy2();
        study3 = getMockedStudy3();
        study4 = getMockedStudy4();
        studies = getMockedStudies();
        studyDto1 = convertToDto(study1);
        studyDto2 = convertToDto(study2);
        studyDto3 = convertToDto(study3);
        studyDto4 = convertToDto(study4);
        studyDtos = List.of(studyDto1, studyDto2, studyDto3, studyDto4);

        given(modelMapper.map(studyDto1, Study.class)).willReturn(study1);
        given(modelMapper.map(studyDto2, Study.class)).willReturn(study2);
        given(modelMapper.map(studyDto3, Study.class)).willReturn(study3);
        given(modelMapper.map(studyDto4, Study.class)).willReturn(study4);
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() throws Exception {
        given(studyService.findAll()).willReturn(studyDtos);
        mockMvc.perform(get(STUDIES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDIES_VIEW))
              .andExpect(model().attribute("studies", studies));
        verify(studyService).findAll();
    }

    @Test
    void getStudyByIdPage_withValidId_shouldReturnStudyDetailsPage() throws Exception {
        given(studyService.findById(anyInt())).willReturn(studyDto1);
        mockMvc.perform(get(STUDIES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDY_DETAILS_VIEW))
              .andExpect(model().attribute("study", study1));
        verify(studyService).findById(VALID_ID);
    }

    @Test
    void getStudyByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(STUDIES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).findById(INVALID_ID);
    }

    @Test
    void getSaveStudyPage_withNegativeId_shouldReturnSaveStudyPage() throws Exception {
        mockMvc.perform(get(STUDIES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDY_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("studyDto", new StudyDto()));
    }

    @Test
    void getSaveStudyPage_withValidId_shouldReturnUpdateStudyPage() throws Exception {
        given(studyService.findById(anyInt())).willReturn(studyDto1);
        mockMvc.perform(get(STUDIES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDY_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("studyDto", studyDto1));
    }

    @Test
    void getSaveStudyPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(STUDIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveStudy() throws Exception {
        mockMvc.perform(post(STUDIES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(studyDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).save(any(StudyDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateStudyWithGivenId() throws Exception {
        mockMvc.perform(post(STUDIES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(studyDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).updateById(studyDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(STUDIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(studyDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).updateById(any(StudyDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveStudyWithGivenIdFromList() throws Exception {
        mockMvc.perform(get(STUDIES + "/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(anyInt());
        mockMvc.perform(get(STUDIES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(StudyDto studyDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", studyDto.getTitle());
        params.add("institution", studyDto.getInstitution());
        params.add("description", studyDto.getDescription());
        params.add("type", studyDto.getType().toString());
        params.add("startedAt", studyDto.getStartedAt().toString());
        params.add("finishedAt", studyDto.getFinishedAt().toString());
        return params;
    }

    private StudyDto convertToDto(Study study) {
        return StudyDto.builder()
              .id(study.getId())
              .title(study.getTitle())
              .institution(study.getInstitution())
              .description(study.getDescription())
              .type(study.getType())
              .startedAt(study.getStartedAt())
              .finishedAt(study.getFinishedAt())
              .build();
    }
}
