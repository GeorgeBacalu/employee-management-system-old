package com.project.ems.unit.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
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

import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_MENTOR_VIEW;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentorDto1;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage1;
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
class MentorControllerTest {

    @InjectMocks
    private MentorController mentorController;

    @Mock
    private MentorService mentorService;

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;

    private Mentor mentor;
    private List<Mentor> mentors;
    private MentorDto mentorDto;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor = getMockedMentor1();
        mentors = getMockedMentorsPage1();
        mentorDto = getMockedMentorDto1();
        mentorDtos = mentorService.convertToDtos(mentors);
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() {
        PageImpl<MentorDto> mentorDtosPage = new PageImpl<>(mentorDtos);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrMentors = mentorDtosPage.getTotalElements();
        int nrPages = mentorDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        given(mentorService.findAllByKey(pageable, MENTOR_FILTER_KEY)).willReturn(mentorDtosPage);
        given(model.getAttribute("mentors")).willReturn(mentors);
        given(model.getAttribute("nrMentors")).willReturn(nrMentors);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("key")).willReturn(MENTOR_FILTER_KEY);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("startIndexCurrentPage")).willReturn(getStartIndexCurrentPage(page, size));
        given(model.getAttribute("endIndexCurrentPage")).willReturn(getEndIndexCurrentPage(page, size, nrMentors));
        given(model.getAttribute("startIndexPageNavigation")).willReturn(getStartIndexPageNavigation(page, nrPages));
        given(model.getAttribute("endIndexPageNavigation")).willReturn(getEndIndexPageNavigation(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = mentorController.getAllMentorsPage(model, pageable, MENTOR_FILTER_KEY);
        assertThat(viewName).isEqualTo(MENTORS_VIEW);
        assertThat(model.getAttribute("mentors")).isEqualTo(mentors);
        assertThat(model.getAttribute("nrMentors")).isEqualTo(nrMentors);
        assertThat(model.getAttribute("nrPages")).isEqualTo(nrPages);
        assertThat(model.getAttribute("page")).isEqualTo(page);
        assertThat(model.getAttribute("size")).isEqualTo(size);
        assertThat(model.getAttribute("key")).isEqualTo(MENTOR_FILTER_KEY);
        assertThat(model.getAttribute("field")).isEqualTo(field);
        assertThat(model.getAttribute("direction")).isEqualTo(direction);
        assertThat(model.getAttribute("startIndexCurrentPage")).isEqualTo(getStartIndexCurrentPage(page, size));
        assertThat(model.getAttribute("endIndexCurrentPage")).isEqualTo(getEndIndexCurrentPage(page, size, nrMentors));
        assertThat(model.getAttribute("startIndexPageNavigation")).isEqualTo(getStartIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("endIndexPageNavigation")).isEqualTo(getEndIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfMentorsFilteredByKey() {
        PageImpl<MentorDto> mentorDtosPage = new PageImpl<>(mentorDtos);
        int page = mentorDtosPage.getNumber();
        int size = mentorDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(MENTOR_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = mentorController.findAllByKey(new SearchRequest(page, size, MENTOR_FILTER_KEY, sort), redirectAttributes);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(MENTOR_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() {
        given(mentorService.findById(anyInt())).willReturn(mentorDto);
        given(model.getAttribute(anyString())).willReturn(mentor);
        String viewName = mentorController.getMentorByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(MENTOR_DETAILS_VIEW);
        assertThat(model.getAttribute("mentor")).isEqualTo(mentor);
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> mentorController.getMentorByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveMentorPage_withNegativeId_shouldReturnSaveMentorPage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("mentorDto")).willReturn(new MentorDto());
        String viewName = mentorController.getSaveMentorPage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_MENTOR_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("mentorDto")).isEqualTo(new MentorDto());
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnUpdateMentorPage() {
        given(mentorService.findById(anyInt())).willReturn(mentorDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("mentorDto")).willReturn(mentorDto);
        String viewName = mentorController.getSaveMentorPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_MENTOR_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("mentorDto")).isEqualTo(mentorDto);
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> mentorController.getSaveMentorPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveMentor() {
        String viewName = mentorController.save(mentorDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        verify(mentorService).save(mentorDto);
    }

    @Test
    void save_withValidId_shouldUpdateMentorWithGivenId() {
        String viewName = mentorController.save(mentorDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        verify(mentorService).updateById(mentorDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(mentorDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> mentorController.save(mentorDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() {
        PageImpl<MentorDto> mentorDtosPage = new PageImpl<>(mentorDtos);
        given(mentorService.findAllByKey(pageable, MENTOR_FILTER_KEY)).willReturn(mentorDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(mentorDtosPage.getNumber());
        given(redirectAttributes.getAttribute("size")).willReturn(mentorDtosPage.getSize());
        given(redirectAttributes.getAttribute("key")).willReturn(MENTOR_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(getSortField(pageable) + ',' +  getSortDirection(pageable));
        String viewName = mentorController.deleteById(VALID_ID, redirectAttributes, pageable, MENTOR_FILTER_KEY);
        verify(mentorService).deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(mentorDtosPage.getNumber());
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(mentorDtosPage.getSize());
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(MENTOR_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(getSortField(pageable) + ',' +  getSortDirection(pageable));
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> mentorController.deleteById(INVALID_ID, redirectAttributes, pageable, MENTOR_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
