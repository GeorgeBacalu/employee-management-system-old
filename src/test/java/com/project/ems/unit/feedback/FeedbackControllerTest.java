package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackController;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.user.UserService;
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

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
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
    private ModelMapper modelMapper;

    private Feedback feedback;
    private List<Feedback> feedbacks;
    private FeedbackDto feedbackDto;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedback = getMockedFeedback1();
        feedbacks = getMockedFeedbacks();
        feedbackDto = convertToDto(modelMapper, feedback);
        feedbackDtos = convertToDtoList(modelMapper, feedbacks);
    }

    @Test
    void getAllFeedbacksPage_shouldReturnFeedbacksPage() {
        given(feedbackService.findAll()).willReturn(feedbackDtos);
        given(model.getAttribute(anyString())).willReturn(feedbacks);
        String viewName = feedbackController.getAllFeedbacksPage(model);
        assertThat(viewName).isEqualTo(FEEDBACKS_VIEW);
        assertThat(model.getAttribute("feedbacks")).isEqualTo(feedbacks);
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
        String viewName = feedbackController.deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_FEEDBACKS_VIEW);
        verify(feedbackService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> feedbackController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
