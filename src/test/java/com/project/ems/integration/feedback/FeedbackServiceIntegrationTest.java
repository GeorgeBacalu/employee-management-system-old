package com.project.ems.integration.feedback;

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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FeedbackServiceIntegrationTest {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @MockBean
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @MockBean
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
        feedbackDto1 = convertToDto(modelMapper, feedback1);
        feedbackDto2 = convertToDto(modelMapper, feedback2);
        feedbackDtos = convertToDtoList(modelMapper, feedbacks);
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

    private Stream<Arguments> paginationArguments() {
        List<Feedback> feedbacksPage1 = getMockedFeedbacksPage1();
        List<Feedback> feedbacksPage2 = getMockedFeedbacksPage2();
        List<Feedback> feedbacksPage3 = getMockedFeedbacksPage3();
        List<FeedbackDto> feedbackDtosPage1 = convertToDtoList(modelMapper, feedbacksPage1);
        List<FeedbackDto> feedbackDtosPage2 = convertToDtoList(modelMapper, feedbacksPage2);
        List<FeedbackDto> feedbackDtosPage3 = convertToDtoList(modelMapper, feedbacksPage3);
        return Stream.of(Arguments.of(0, 2, "id", "asc", FEEDBACK_FILTER_KEY, new PageImpl<>(feedbacksPage1), new PageImpl<>(feedbackDtosPage1)),
                         Arguments.of(1, 2, "id", "asc", FEEDBACK_FILTER_KEY, new PageImpl<>(feedbacksPage2), new PageImpl<>(feedbackDtosPage2)),
                         Arguments.of(2, 2, "id", "asc", FEEDBACK_FILTER_KEY, new PageImpl<>(Collections.emptyList()), new PageImpl<>(Collections.emptyList())),
                         Arguments.of(0, 2, "id", "asc", "", new PageImpl<>(feedbacksPage1), new PageImpl<>(feedbackDtosPage1)),
                         Arguments.of(1, 2, "id", "asc", "", new PageImpl<>(feedbacksPage2), new PageImpl<>(feedbackDtosPage2)),
                         Arguments.of(2, 2, "id", "asc", "", new PageImpl<>(feedbacksPage3), new PageImpl<>(feedbackDtosPage3)));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, PageImpl<Feedback> entityPage, PageImpl<FeedbackDto> dtoPage) {
        if(key.trim().equals("")) {
            given(feedbackRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(feedbackRepository.findAllByKey(any(Pageable.class), anyString())).willReturn(entityPage);
        }
        Page<FeedbackDto> result = feedbackService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
