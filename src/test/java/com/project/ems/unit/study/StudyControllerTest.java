package com.project.ems.unit.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.Study;
import com.project.ems.study.StudyController;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_STUDY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDY_DETAILS_VIEW;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyControllerTest {

    @InjectMocks
    private StudyController studyController;

    @Mock
    private StudyService studyService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Study study;
    private List<Study> studies;
    private StudyDto studyDto;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        study = getMockedStudy1();
        studies = getMockedStudies();
        studyDto = convertToDto(modelMapper, study);
        studyDtos = convertToDtoList(modelMapper, studies);
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() {
        given(studyService.findAll()).willReturn(studyDtos);
        given(model.getAttribute(anyString())).willReturn(studies);
        String viewName = studyController.getAllStudiesPage(model);
        assertThat(viewName).isEqualTo(STUDIES_VIEW);
        assertThat(model.getAttribute("studies")).isEqualTo(studies);
    }

    @Test
    void getStudyByIdPage_withValidId_shouldReturnStudyDetailsPage() {
        given(studyService.findById(anyInt())).willReturn(studyDto);
        given(model.getAttribute(anyString())).willReturn(study);
        String viewName = studyController.getStudyByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(STUDY_DETAILS_VIEW);
        assertThat(model.getAttribute("study")).isEqualTo(study);
    }

    @Test
    void getStudyByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> studyController.getStudyByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveStudyPage_withNegativeId_shouldReturnSaveStudyPage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("studyDto")).willReturn(new StudyDto());
        String viewName = studyController.getSaveStudyPage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_STUDY_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("studyDto")).isEqualTo(new StudyDto());
    }

    @Test
    void getSaveStudyPage_withValidId_shouldReturnUpdateStudyPage() {
        given(studyService.findById(anyInt())).willReturn(studyDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("studyDto")).willReturn(studyDto);
        String viewName = studyController.getSaveStudyPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_STUDY_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("studyDto")).isEqualTo(studyDto);
    }

    @Test
    void getSaveStudyPage_withInvalidId_shouldThrowException() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> studyController.getSaveStudyPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveStudy() {
        String viewName = studyController.save(studyDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        verify(studyService).save(studyDto);
    }

    @Test
    void save_withValidId_shouldUpdateStudyWithGivenId() {
        String viewName = studyController.save(studyDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        verify(studyService).updateById(studyDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(studyDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> studyController.save(studyDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveStudyWithGivenIdFromList() {
        String viewName = studyController.deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        verify(studyService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> studyController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
