package com.project.ems.unit.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRepository;
import com.project.ems.feedback.FeedbackServiceImpl;
import com.project.ems.user.User;
import com.project.ems.user.UserService;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage3;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private Clock clock;

    private static final ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.of("Europe/Bucharest"));

    @Captor
    private ArgumentCaptor<Feedback> feedbackCaptor;

    private Feedback feedback1;
    private Feedback feedback2;
    private List<Feedback> feedbacks;
    private List<Feedback> feedbacksPage1;
    private List<Feedback> feedbacksPage2;
    private List<Feedback> feedbacksPage3;
    private User user;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;
    private List<FeedbackDto> feedbackDtosPage1;
    private List<FeedbackDto> feedbackDtosPage2;
    private List<FeedbackDto> feedbackDtosPage3;

    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedbacks = getMockedFeedbacks();
        feedbacksPage1 = getMockedFeedbacksPage1();
        feedbacksPage2 = getMockedFeedbacksPage2();
        feedbacksPage3 = getMockedFeedbacksPage3();
        user = getMockedUser2();
        feedbackDto1 = convertToDto(modelMapper, feedback1);
        feedbackDto2 = convertToDto(modelMapper, feedback2);
        feedbackDtos = convertToDtoList(modelMapper, feedbacks);
        feedbackDtosPage1 = convertToDtoList(modelMapper, feedbacksPage1);
        feedbackDtosPage2 = convertToDtoList(modelMapper, feedbacksPage2);
        feedbackDtosPage3 = convertToDtoList(modelMapper, feedbacksPage3);
    }

    @Test
    void findAll_shouldReturnListOfFeedbacks() {
        given(feedbackRepository.findAll()).willReturn(feedbacks);
        List<FeedbackDto> result = feedbackService.findAll();
        assertThat(result).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_withValidId_shouldReturnFeedbackWithGivenId() {
        given(feedbackRepository.findById(anyInt())).willReturn(Optional.ofNullable(feedback1));
        FeedbackDto result = feedbackService.findById(VALID_ID);
        assertThat(result).isEqualTo(feedbackDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> feedbackService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddFeedbackToList() {
        given(clock.getZone()).willReturn(zonedDateTime.getZone());
        given(clock.instant()).willReturn(zonedDateTime.toInstant());
        feedback1.setSentAt(zonedDateTime.toLocalDateTime());
        given(feedbackRepository.save(any(Feedback.class))).willReturn(feedback1);
        FeedbackDto result = feedbackService.save(feedbackDto1);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, feedback1));
    }

    @Test
    void updateById_withValidId_shouldUpdateFeedbackWithGivenId() {
        Feedback feedback = feedback2; feedback.setId(VALID_ID);
        feedback.setSentAt(feedback1.getSentAt());
        given(feedbackRepository.findById(anyInt())).willReturn(Optional.ofNullable(feedback1));
        given(userService.findEntityById(anyInt())).willReturn(user);
        given(feedbackRepository.save(any(Feedback.class))).willReturn(feedback);
        FeedbackDto result = feedbackService.updateById(feedbackDto2, VALID_ID);
        verify(feedbackRepository).save(feedbackCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, feedbackCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> feedbackService.updateById(feedbackDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() {
        given(feedbackRepository.findById(anyInt())).willReturn(Optional.ofNullable(feedback1));
        feedbackService.deleteById(VALID_ID);
        verify(feedbackRepository).delete(feedback1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> feedbackService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(FEEDBACK_NOT_FOUND, INVALID_ID));
        verify(feedbackRepository, never()).delete(any(Feedback.class));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfFeedbacksFilteredByKeyPage1() {
        given(feedbackRepository.findAllByKey(pageable, FEEDBACK_FILTER_KEY)).willReturn(new PageImpl<>(feedbacksPage1));
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageable, FEEDBACK_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(feedbackDtosPage1);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfFeedbacksFilteredByKeyPage2() {
        given(feedbackRepository.findAllByKey(pageable2, FEEDBACK_FILTER_KEY)).willReturn(new PageImpl<>(feedbacksPage2));
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageable2, FEEDBACK_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(feedbackDtosPage2);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfFeedbacksFilteredByKeyPage3() {
        given(feedbackRepository.findAllByKey(pageable3, FEEDBACK_FILTER_KEY)).willReturn(new PageImpl<>(Collections.emptyList()));
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageable3, FEEDBACK_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(Collections.emptyList());
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfFeedbacksPage1() {
        given(feedbackRepository.findAll(pageable)).willReturn(new PageImpl<>(feedbacksPage1));
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageable, "");
        assertThat(result.getContent()).isEqualTo(feedbackDtosPage1);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfFeedbacksPage2() {
        given(feedbackRepository.findAll(pageable2)).willReturn(new PageImpl<>(feedbacksPage2));
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageable2, "");
        assertThat(result.getContent()).isEqualTo(feedbackDtosPage2);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfFeedbacksPage3() {
        given(feedbackRepository.findAll(pageable3)).willReturn(new PageImpl<>(feedbacksPage3));
        Page<FeedbackDto> result = feedbackService.findAllByKey(pageable3, "");
        assertThat(result.getContent()).isEqualTo(feedbackDtosPage3);
    }
}
