package com.project.ems.unit.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.Study;
import com.project.ems.study.StudyController;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_STUDY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDY_DETAILS_VIEW;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage1;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;
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
    private RedirectAttributes redirectAttributes;

    @Spy
    private ModelMapper modelMapper;

    private Study study;
    private List<Study> studies;
    private StudyDto studyDto;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        study = getMockedStudy1();
        studies = getMockedStudiesPage1();
        studyDto = convertToDto(modelMapper, study);
        studyDtos = convertToDtoList(modelMapper, studies);
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() {
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtos);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        given(studyService.findAllByKey(pageable, STUDY_FILTER_KEY)).willReturn(studyDtosPage);
        given(model.getAttribute("studies")).willReturn(studies);
        given(model.getAttribute("nrStudies")).willReturn(nrStudies);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("key")).willReturn(STUDY_FILTER_KEY);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("startIndexCurrentPage")).willReturn(getStartIndexCurrentPage(page, size));
        given(model.getAttribute("endIndexCurrentPage")).willReturn(getEndIndexCurrentPage(page, size, nrStudies));
        given(model.getAttribute("startIndexPageNavigation")).willReturn(getStartIndexPageNavigation(page, nrPages));
        given(model.getAttribute("endIndexPageNavigation")).willReturn(getEndIndexPageNavigation(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = studyController.getAllStudiesPage(model, pageable, STUDY_FILTER_KEY);
        assertThat(viewName).isEqualTo(STUDIES_VIEW);
        assertThat(model.getAttribute("studies")).isEqualTo(studies);
        assertThat(model.getAttribute("nrStudies")).isEqualTo(nrStudies);
        assertThat(model.getAttribute("nrPages")).isEqualTo(nrPages);
        assertThat(model.getAttribute("page")).isEqualTo(page);
        assertThat(model.getAttribute("size")).isEqualTo(size);
        assertThat(model.getAttribute("key")).isEqualTo(STUDY_FILTER_KEY);
        assertThat(model.getAttribute("field")).isEqualTo(field);
        assertThat(model.getAttribute("direction")).isEqualTo(direction);
        assertThat(model.getAttribute("startIndexCurrentPage")).isEqualTo(getStartIndexCurrentPage(page, size));
        assertThat(model.getAttribute("endIndexCurrentPage")).isEqualTo(getEndIndexCurrentPage(page, size, nrStudies));
        assertThat(model.getAttribute("startIndexPageNavigation")).isEqualTo(getStartIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("endIndexPageNavigation")).isEqualTo(getEndIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfStudiesFilteredByKey() {
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtos);
        int page = studyDtosPage.getNumber();
        int size = studyDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(STUDY_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = studyController.findAllByKey(new SearchRequest(page, size, STUDY_FILTER_KEY, sort), redirectAttributes);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(STUDY_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
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
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtos);
        given(studyService.findAllByKey(pageable, STUDY_FILTER_KEY)).willReturn(studyDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(studyDtosPage.getNumber());
        given(redirectAttributes.getAttribute("size")).willReturn(studyDtosPage.getSize());
        given(redirectAttributes.getAttribute("key")).willReturn(STUDY_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(getSortField(pageable) + ',' +  getSortDirection(pageable));
        String viewName = studyController.deleteById(VALID_ID, redirectAttributes, pageable, STUDY_FILTER_KEY);
        verify(studyService).deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_STUDIES_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(studyDtosPage.getNumber());
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(studyDtosPage.getSize());
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(STUDY_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(getSortField(pageable) + ',' +  getSortDirection(pageable));
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(studyService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> studyController.deleteById(INVALID_ID, redirectAttributes, pageable, STUDY_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
