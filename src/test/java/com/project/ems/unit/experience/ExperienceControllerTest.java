package com.project.ems.unit.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceController;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDto1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
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
class ExperienceControllerTest {

    @InjectMocks
    private ExperienceController experienceController;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;
    
    private Experience experience;
    private List<Experience> experiences;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiences = getMockedExperiencesPage1();
        experienceDto = getMockedExperienceDto1();
        experienceDtos = experienceService.convertToDtos(experiences);
    }

    @Test
    void getAllExperiencesPage_shouldReturnExperiencesPage() {
        PageImpl<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtos);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrExperiences = experienceDtosPage.getTotalElements();
        int nrPages = experienceDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        given(experienceService.findAllByKey(pageable, EXPERIENCE_FILTER_KEY)).willReturn(experienceDtosPage);
        given(model.getAttribute("experiences")).willReturn(experiences);
        given(model.getAttribute("nrExperiences")).willReturn(nrExperiences);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("key")).willReturn(EXPERIENCE_FILTER_KEY);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("startIndexCurrentPage")).willReturn(getStartIndexCurrentPage(page, size));
        given(model.getAttribute("endIndexCurrentPage")).willReturn(getEndIndexCurrentPage(page, size, nrExperiences));
        given(model.getAttribute("startIndexPageNavigation")).willReturn(getStartIndexPageNavigation(page, nrPages));
        given(model.getAttribute("endIndexPageNavigation")).willReturn(getEndIndexPageNavigation(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = experienceController.getAllExperiencesPage(model, pageable, EXPERIENCE_FILTER_KEY);
        assertThat(viewName).isEqualTo(EXPERIENCES_VIEW);
        assertThat(model.getAttribute("experiences")).isEqualTo(experiences);
        assertThat(model.getAttribute("nrExperiences")).isEqualTo(nrExperiences);
        assertThat(model.getAttribute("nrPages")).isEqualTo(nrPages);
        assertThat(model.getAttribute("page")).isEqualTo(page);
        assertThat(model.getAttribute("size")).isEqualTo(size);
        assertThat(model.getAttribute("key")).isEqualTo(EXPERIENCE_FILTER_KEY);
        assertThat(model.getAttribute("field")).isEqualTo(field);
        assertThat(model.getAttribute("direction")).isEqualTo(direction);
        assertThat(model.getAttribute("startIndexCurrentPage")).isEqualTo(getStartIndexCurrentPage(page, size));
        assertThat(model.getAttribute("endIndexCurrentPage")).isEqualTo(getEndIndexCurrentPage(page, size, nrExperiences));
        assertThat(model.getAttribute("startIndexPageNavigation")).isEqualTo(getStartIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("endIndexPageNavigation")).isEqualTo(getEndIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfExperiencesFilteredByKey() {
        PageImpl<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtos);
        int page = experienceDtosPage.getNumber();
        int size = experienceDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(EXPERIENCE_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = experienceController.findAllByKey(new SearchRequest(page, size, EXPERIENCE_FILTER_KEY, sort), redirectAttributes);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(EXPERIENCE_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
    }

    @Test
    void getExperienceByIdPage_withValidId_shouldReturnExperienceDetailsPage() {
        given(experienceService.findById(anyInt())).willReturn(experienceDto);
        given(model.getAttribute(anyString())).willReturn(experience);
        String viewName = experienceController.getExperienceByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(EXPERIENCE_DETAILS_VIEW);
        assertThat(model.getAttribute("experience")).isEqualTo(experience);
    }

    @Test
    void getExperienceByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> experienceController.getExperienceByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveExperiencePage_withNegativeId_shouldReturnSaveExperiencePage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("experienceDto")).willReturn(new ExperienceDto());
        String viewName = experienceController.getSaveExperiencePage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("experienceDto")).isEqualTo(new ExperienceDto());
    }

    @Test
    void getSaveExperiencePage_withValidId_shouldReturnUpdateExperiencePage() {
        given(experienceService.findById(anyInt())).willReturn(experienceDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("experienceDto")).willReturn(experienceDto);
        String viewName = experienceController.getSaveExperiencePage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("experienceDto")).isEqualTo(experienceDto);
    }

    @Test
    void getSaveExperiencePage_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> experienceController.getSaveExperiencePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveExperience() {
        String viewName = experienceController.save(experienceDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).save(experienceDto);
    }

    @Test
    void save_withValidId_shouldUpdateExperienceWithGivenId() {
        String viewName = experienceController.save(experienceDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).updateById(experienceDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(experienceDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> experienceController.save(experienceDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenIdFromList() {
        PageImpl<ExperienceDto> experienceDtosPage = new PageImpl<>(experienceDtos);
        given(experienceService.findAllByKey(pageable, EXPERIENCE_FILTER_KEY)).willReturn(experienceDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(experienceDtosPage.getNumber());
        given(redirectAttributes.getAttribute("size")).willReturn(experienceDtosPage.getSize());
        given(redirectAttributes.getAttribute("key")).willReturn(EXPERIENCE_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(getSortField(pageable) + ',' +  getSortDirection(pageable));
        String viewName = experienceController.deleteById(VALID_ID, redirectAttributes, pageable, EXPERIENCE_FILTER_KEY);
        verify(experienceService).deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(experienceDtosPage.getNumber());
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(experienceDtosPage.getSize());
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(EXPERIENCE_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(getSortField(pageable) + ',' +  getSortDirection(pageable));
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> experienceController.deleteById(INVALID_ID, redirectAttributes, pageable, EXPERIENCE_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
