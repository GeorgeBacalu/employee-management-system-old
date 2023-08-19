package com.project.ems.integration.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceController;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceService;
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

import static com.project.ems.constants.EndpointConstants.EXPERIENCES;
import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDto1;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDtosFirstPage;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesFirstPage;
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

@WebMvcTest(ExperienceController.class)
class ExperienceControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperienceService experienceService;

    private Experience experience;
    private List<Experience> experiencesPage1;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtosPage1;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiencesPage1 = getMockedExperiencesFirstPage();
        experienceDto = getMockedExperienceDto1();
        experienceDtosPage1 = getMockedExperienceDtosFirstPage();
    }

    @Test
    void getAllExperiencesPage_shouldReturnExperiencesPage() throws Exception {
        PageImpl<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        given(experienceService.findAllByKey(any(Pageable.class), anyString())).willReturn(experienceDtosPage);
        given(experienceService.convertToEntities(experienceDtosPage.getContent())).willReturn(experiencesPage1);
        int page = pageable.getPageNumber();
        int size = experienceDtosPage1.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrExperiences = experienceDtosPage.getTotalElements();
        int nrPages = experienceDtosPage.getTotalPages();
        mockMvc.perform(get(EXPERIENCES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCES_VIEW))
              .andExpect(model().attribute("experiences", experiencesPage1))
              .andExpect(model().attribute("nrExperiences", nrExperiences))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size)))
              .andExpect(model().attribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrExperiences)))
              .andExpect(model().attribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction)));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfExperiencesFilteredByKey() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(experienceDtosPage1.size()))
                    .param("key", EXPERIENCE_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(EXPERIENCES + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getExperienceByIdPage_withValidId_shouldReturnExperienceDetailsPage() throws Exception {
        given(experienceService.findById(anyInt())).willReturn(experienceDto);
        given(experienceService.convertToEntity(experienceDto)).willReturn(experience);
        mockMvc.perform(get(EXPERIENCES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCE_DETAILS_VIEW))
              .andExpect(model().attribute("experience", experience));
        verify(experienceService).findById(VALID_ID);
    }

    @Test
    void getExperienceByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EXPERIENCES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).findById(INVALID_ID);
    }

    @Test
    void getSaveExperiencePage_withNegativeId_shouldReturnSaveExperiencePage() throws Exception {
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("experienceDto", new ExperienceDto()));
    }

    @Test
    void getSaveExperiencePage_withValidId_shouldReturnUpdateExperiencePage() throws Exception {
        given(experienceService.findById(anyInt())).willReturn(experienceDto);
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("experienceDto", experienceDto));
    }

    @Test
    void getSaveExperiencePage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveExperience() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(experienceDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).save(any(ExperienceDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateExperienceWithGivenId() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(experienceDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).updateById(experienceDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(experienceDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).updateById(any(ExperienceDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenIdFromList() throws Exception {
        PageImpl<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtosPage1);
        given(experienceService.findAllByKey(any(Pageable.class), anyString())).willReturn(experienceDtosPage);
        mockMvc.perform(get(EXPERIENCES + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(experienceDtosPage1.size()))
                    .param("key", EXPERIENCE_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(EXPERIENCES + "?page=*&size=*&key=*&sort=*"));
        verify(experienceService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(anyInt());
        mockMvc.perform(get(EXPERIENCES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(ExperienceDto experienceDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", experienceDto.getTitle());
        params.add("organization", experienceDto.getOrganization());
        params.add("description", experienceDto.getDescription());
        params.add("type", experienceDto.getType().name());
        params.add("startedAt", experienceDto.getStartedAt().toString());
        params.add("finishedAt", experienceDto.getFinishedAt().toString());
        return params;
    }
}
