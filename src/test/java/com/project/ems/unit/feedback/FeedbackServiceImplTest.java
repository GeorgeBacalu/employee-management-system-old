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
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDto1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDto2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage3;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
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
    private User user;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;
    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedbacks = getMockedFeedbacks();
        user = getMockedUser2();
        feedbackDto1 = getMockedFeedbackDto1();
        feedbackDto2 = getMockedFeedbackDto2();
        feedbackDtos = feedbackService.convertToDtos(feedbacks);
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
        assertThat(result).isEqualTo(feedbackService.convertToDto(feedback1));
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
        assertThat(result).isEqualTo(feedbackService.convertToDto(feedbackCaptor.getValue()));
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

    @ParameterizedTest
    @CsvSource({ "1, ${FEEDBACK_FILTER_KEY}", "2, ${FEEDBACK_FILTER_KEY}", "3, ${FEEDBACK_FILTER_KEY}", "1, ''", "2, ''", "3, ''"  })
    void findAllByKey_shouldReturnListOfFeedbacksFilteredByKey(int page, String key) {
        Pair<List<Feedback>, Pageable> pair = switch(page) {
            case 1 -> Pair.of(getMockedFeedbacksPage1(), pageable);
            case 2 -> Pair.of(getMockedFeedbacksPage2(), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : getMockedFeedbacksPage3(), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
        Page<Feedback> filteredFeedbacksPage = new PageImpl<>(pair.getLeft());
        if(key.equals("")) {
            given(feedbackRepository.findAll(any(Pageable.class))).willReturn(filteredFeedbacksPage);
        } else {
            given(feedbackRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredFeedbacksPage);
        }
        Page<FeedbackDto> result = feedbackService.findAllByKey(pair.getRight(), key);
        assertThat(result.getContent()).isEqualTo(feedbackService.convertToDtos(pair.getLeft()));
    }
}
