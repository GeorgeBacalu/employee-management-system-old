package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackController;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.user.UserService;
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

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
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
class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private UserService userService;

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;

    @Spy
    private ModelMapper modelMapper;

    private Feedback feedback;
    private List<Feedback> feedbacks;
    private FeedbackDto feedbackDto;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback = getMockedFeedback1();
        feedbacks = getMockedFeedbacksPage1();
        feedbackDto = convertToDto(modelMapper, feedback);
        feedbackDtos = convertToDtoList(modelMapper, feedbacks);
    }

    @Test
    void getAllFeedbacksPage_shouldReturnFeedbacksPage() {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtos);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        int startIndexCurrentPage = getStartIndexCurrentPage(page, size);
        long endIndexCurrentPage = getEndIndexCurrentPage(page, size, nrFeedbacks);
        int startIndexPageNavigation = getStartIndexPageNavigation(page, nrPages);
        int endIndexPageNavigation = getEndIndexPageNavigation(page, nrPages);
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        given(feedbackService.findAllByKey(pageable, FEEDBACK_FILTER_KEY)).willReturn(feedbackDtosPage);
        given(model.getAttribute("feedbacks")).willReturn(feedbacks);
        given(model.getAttribute("nrFeedbacks")).willReturn(nrFeedbacks);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("key")).willReturn(FEEDBACK_FILTER_KEY);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("startIndexCurrentPage")).willReturn(startIndexCurrentPage);
        given(model.getAttribute("endIndexCurrentPage")).willReturn(endIndexCurrentPage);
        given(model.getAttribute("startIndexPageNavigation")).willReturn(startIndexPageNavigation);
        given(model.getAttribute("endIndexPageNavigation")).willReturn(endIndexPageNavigation);
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = feedbackController.getAllFeedbacksPage(model, pageable, FEEDBACK_FILTER_KEY);
        assertThat(viewName).isEqualTo(FEEDBACKS_VIEW);
        assertThat(model.getAttribute("feedbacks")).isEqualTo(feedbacks);
        assertThat(model.getAttribute("nrFeedbacks")).isEqualTo(nrFeedbacks);
        assertThat(model.getAttribute("nrPages")).isEqualTo(nrPages);
        assertThat(model.getAttribute("page")).isEqualTo(page);
        assertThat(model.getAttribute("size")).isEqualTo(size);
        assertThat(model.getAttribute("key")).isEqualTo(FEEDBACK_FILTER_KEY);
        assertThat(model.getAttribute("field")).isEqualTo(field);
        assertThat(model.getAttribute("direction")).isEqualTo(direction);
        assertThat(model.getAttribute("startIndexCurrentPage")).isEqualTo(startIndexCurrentPage);
        assertThat(model.getAttribute("endIndexCurrentPage")).isEqualTo(endIndexCurrentPage);
        assertThat(model.getAttribute("startIndexPageNavigation")).isEqualTo(startIndexPageNavigation);
        assertThat(model.getAttribute("endIndexPageNavigation")).isEqualTo(endIndexPageNavigation);
        assertThat(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfFeedbacksFilteredByKey() {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtos);
        int page = feedbackDtosPage.getNumber();
        int size = feedbackDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(FEEDBACK_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = feedbackController.findAllByKey(new SearchRequest(page, size, FEEDBACK_FILTER_KEY, sort), redirectAttributes);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(FEEDBACK_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
    }

    @Test
    void getFeedbackByIdPage_withValidId_shouldReturnFeedbackDetailsPage() {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto);
        given(model.getAttribute(anyString())).willReturn(feedback);
        String viewName = feedbackController.getFeedbackByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(FEEDBACK_DETAILS_VIEW);
        assertThat(model.getAttribute("feedback")).isEqualTo(feedback);
    }

    @Test
    void getFeedbackByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> feedbackController.getFeedbackByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveFeedbackPage_withNegativeId_shouldReturnSaveFeedbackPage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("feedbackDto")).willReturn(new FeedbackDto());
        String viewName = feedbackController.getSaveFeedbackPage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_FEEDBACK_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("feedbackDto")).isEqualTo(new FeedbackDto());
    }

    @Test
    void getSaveFeedbackPage_withValidId_shouldReturnUpdateFeedbackPage() {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("feedbackDto")).willReturn(feedbackDto);
        String viewName = feedbackController.getSaveFeedbackPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_FEEDBACK_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("feedbackDto")).isEqualTo(feedbackDto);
    }

    @Test
    void getSaveFeedbackPage_withInvalidId_shouldThrowException() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> feedbackController.getSaveFeedbackPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveFeedback() {
        String viewName = feedbackController.save(feedbackDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        verify(feedbackService).save(feedbackDto);
    }

    @Test
    void save_withValidId_shouldUpdateFeedbackWithGivenId() {
        String viewName = feedbackController.save(feedbackDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        verify(feedbackService).updateById(feedbackDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(feedbackDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> feedbackController.save(feedbackDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtos);
        int page = feedbackDtosPage.getNumber();
        int size = feedbackDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(feedbackService.findAllByKey(pageable, FEEDBACK_FILTER_KEY)).willReturn(feedbackDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(FEEDBACK_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = feedbackController.deleteById(VALID_ID, redirectAttributes, pageable, FEEDBACK_FILTER_KEY);
        verify(feedbackService).deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(FEEDBACK_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> feedbackController.deleteById(INVALID_ID, redirectAttributes, pageable, FEEDBACK_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
