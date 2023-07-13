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
import org.modelmapper.ModelMapper;
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
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy10;
import static com.project.ems.mock.StudyMock.getMockedStudy11;
import static com.project.ems.mock.StudyMock.getMockedStudy12;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
import static com.project.ems.mock.StudyMock.getMockedStudy3;
import static com.project.ems.mock.StudyMock.getMockedStudy4;
import static com.project.ems.mock.StudyMock.getMockedStudy5;
import static com.project.ems.mock.StudyMock.getMockedStudy6;
import static com.project.ems.mock.StudyMock.getMockedStudy7;
import static com.project.ems.mock.StudyMock.getMockedStudy8;
import static com.project.ems.mock.StudyMock.getMockedStudy9;
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

    @MockBean
    private ModelMapper modelMapper;

    private Study study1;
    private Study study2;
    private Study study3;
    private Study study4;
    private Study study5;
    private Study study6;
    private Study study7;
    private Study study8;
    private Study study9;
    private Study study10;
    private Study study11;
    private Study study12;
    private List<Study> studies;
    private List<Study> studiesFirstPage;
    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private StudyDto studyDto3;
    private StudyDto studyDto4;
    private StudyDto studyDto5;
    private StudyDto studyDto6;
    private StudyDto studyDto7;
    private StudyDto studyDto8;
    private StudyDto studyDto9;
    private StudyDto studyDto10;
    private StudyDto studyDto11;
    private StudyDto studyDto12;
    private List<StudyDto> studyDtos;
    private List<StudyDto> studyDtosFirstPage;

    @BeforeEach
    void setUp() {
        study1 = getMockedStudy1();
        study2 = getMockedStudy2();
        study3 = getMockedStudy3();
        study4 = getMockedStudy4();
        study5 = getMockedStudy5();
        study6 = getMockedStudy6();
        study7 = getMockedStudy7();
        study8 = getMockedStudy8();
        study9 = getMockedStudy9();
        study10 = getMockedStudy10();
        study11 = getMockedStudy11();
        study12 = getMockedStudy12();
        studies = getMockedStudies();
        studiesFirstPage = List.of(study1, study2, study3, study4, study5, study6, study7, study8, study9);
        studyDto1 = convertToDto(study1);
        studyDto2 = convertToDto(study2);
        studyDto3 = convertToDto(study3);
        studyDto4 = convertToDto(study4);
        studyDto5 = convertToDto(study5);
        studyDto6 = convertToDto(study6);
        studyDto7 = convertToDto(study7);
        studyDto8 = convertToDto(study8);
        studyDto9 = convertToDto(study9);
        studyDto10 = convertToDto(study10);
        studyDto11 = convertToDto(study11);
        studyDto12 = convertToDto(study12);
        studyDtos = List.of(studyDto1, studyDto2, studyDto3, studyDto4, studyDto5, studyDto6, studyDto7, studyDto8, studyDto9, studyDto10, studyDto11, studyDto12);
        studyDtosFirstPage = List.of(studyDto1, studyDto2, studyDto3, studyDto4, studyDto5, studyDto6, studyDto7, studyDto8, studyDto9);

        given(modelMapper.map(studyDto1, Study.class)).willReturn(study1);
        given(modelMapper.map(studyDto2, Study.class)).willReturn(study2);
        given(modelMapper.map(studyDto3, Study.class)).willReturn(study3);
        given(modelMapper.map(studyDto4, Study.class)).willReturn(study4);
        given(modelMapper.map(studyDto5, Study.class)).willReturn(study5);
        given(modelMapper.map(studyDto6, Study.class)).willReturn(study6);
        given(modelMapper.map(studyDto7, Study.class)).willReturn(study7);
        given(modelMapper.map(studyDto8, Study.class)).willReturn(study8);
        given(modelMapper.map(studyDto9, Study.class)).willReturn(study9);
        given(modelMapper.map(studyDto10, Study.class)).willReturn(study10);
        given(modelMapper.map(studyDto11, Study.class)).willReturn(study11);
        given(modelMapper.map(studyDto12, Study.class)).willReturn(study12);
    }

    @Test
    void getAllStudiesPage_shouldReturnStudiesPage() throws Exception {
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtosFirstPage);
        given(studyService.findAllByKey(any(Pageable.class), anyString())).willReturn(studyDtosPage);
        int page = pageable.getPageNumber();
        int size = studyDtosFirstPage.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrStudies = studyDtosPage.getTotalElements();
        int nrPages = studyDtosPage.getTotalPages();
        int startIndexCurrentPage = getStartIndexCurrentPage(page, size);
        long endIndexCurrentPage = getEndIndexCurrentPage(page, size, nrStudies);
        int startIndexPageNavigation = getStartIndexPageNavigation(page, nrPages);
        int endIndexPageNavigation = getEndIndexPageNavigation(page, nrPages);
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        mockMvc.perform(get(STUDIES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(STUDIES_VIEW))
              .andExpect(model().attribute("studies", studiesFirstPage))
              .andExpect(model().attribute("nrStudies", nrStudies))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", startIndexCurrentPage))
              .andExpect(model().attribute("endIndexCurrentPage", endIndexCurrentPage))
              .andExpect(model().attribute("startIndexPageNavigation", startIndexPageNavigation))
              .andExpect(model().attribute("endIndexPageNavigation", endIndexPageNavigation))
              .andExpect(model().attribute("searchRequest", searchRequest));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfStudiesFilteredByKey() throws Exception {
        int page = pageable.getPageNumber();
        int size = studyDtosFirstPage.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        mockMvc.perform(post(STUDIES + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size))
                    .param("key", STUDY_FILTER_KEY)
                    .param("sort", field + "," + direction))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(STUDIES + "?page=*&size=*&key=*&sort=*"));
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
        PageImpl<StudyDto> studyDtosPage = new PageImpl<>(studyDtosFirstPage);
        given(studyService.findAllByKey(any(Pageable.class), anyString())).willReturn(studyDtosPage);
        int page = pageable.getPageNumber();
        int size = studyDtosFirstPage.size();
        String sort = getSortField(pageable) + "," + getSortDirection(pageable);
        mockMvc.perform(get(STUDIES + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size))
                    .param("key", STUDY_FILTER_KEY)
                    .param("sort", sort))
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
