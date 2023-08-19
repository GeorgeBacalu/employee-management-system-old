package com.project.ems.integration.study;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.study.Study;
import com.project.ems.study.StudyController;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.STUDIES;
import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_STUDY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDY_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.StudyMock.getMockedStudiesFirstPage;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudyDto1;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosFirstPage;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudyController.class)
class StudyControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyService studyService;

    private Study study;
    private List<Study> studiesPage1;
    private StudyDto studyDto;
    private List<StudyDto> studyDtosPage1;

    @BeforeEach
    void setUp() {
        study = getMockedStudy1();
        studiesPage1 = getMockedStudiesFirstPage();
        studyDto = getMockedStudyDto1();
        studyDtosPage1 = getMockedStudyDtosFirstPage();
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() throws Exception {
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        given(studyService.findAllByKey(any(Pageable.class), anyString())).willReturn(studyDtosPage);
        given(studyService.convertToEntities(studyDtosPage.getContent())).willReturn(studiesPage1);
        int page = pageable.getPageNumber();
        int size = studyDtosPage1.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        mockMvc.perform(get(STUDIES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDIES_VIEW))
              .andExpect(model().attribute("studies", studiesPage1))
              .andExpect(model().attribute("nrStudies", nrStudies))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size)))
              .andExpect(model().attribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrStudies)))
              .andExpect(model().attribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction)));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfStudiesFilteredByKey() throws Exception {
        mockMvc.perform(post(STUDIES + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(studyDtosPage1.size()))
                    .param("key", STUDY_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(STUDIES + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getStudyByIdPage_withValidId_shouldReturnStudyDetailsPage() throws Exception {
        given(studyService.findById(anyInt())).willReturn(studyDto);
        given(studyService.convertToEntity(studyDto)).willReturn(study);
        mockMvc.perform(get(STUDIES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDY_DETAILS_VIEW))
              .andExpect(model().attribute("study", study));
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
        given(studyService.findById(anyInt())).willReturn(studyDto);
        mockMvc.perform(get(STUDIES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_STUDY_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("studyDto", studyDto));
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
                    .params(convertToMultiValueMap(studyDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).save(any(StudyDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateStudyWithGivenId() throws Exception {
        mockMvc.perform(post(STUDIES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(studyDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_STUDIES_VIEW))
              .andExpect(redirectedUrl(STUDIES));
        verify(studyService).updateById(studyDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(STUDY_NOT_FOUND, INVALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(STUDIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(studyDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(studyService).updateById(any(StudyDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveStudyWithGivenIdFromList() throws Exception {
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtosPage1);
        given(studyService.findAllByKey(any(Pageable.class), anyString())).willReturn(studyDtosPage);
        mockMvc.perform(get(STUDIES + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(studyDtosPage1.size()))
                    .param("key", STUDY_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(STUDIES + "?page=*&size=*&key=*&sort=*"));
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
        params.add("type", studyDto.getType().name());
        params.add("startedAt", studyDto.getStartedAt().toString());
        params.add("finishedAt", studyDto.getFinishedAt().toString());
        return params;
    }
}
