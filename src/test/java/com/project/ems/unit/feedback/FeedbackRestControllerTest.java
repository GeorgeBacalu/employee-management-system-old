package com.project.ems.unit.feedback;

import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDto1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDto2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDtosPage1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDtosPage2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDtosPage3;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackRestControllerTest {

    @InjectMocks
    private FeedbackRestController feedbackRestController;

    @Mock
    private FeedbackService feedbackService;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedbackDto1 = getMockedFeedbackDto1();
        feedbackDto2 = getMockedFeedbackDto2();
        feedbackDtos = feedbackService.convertToDtos(getMockedFeedbacks());
    }

    @Test
    void findAll_shouldReturnListOfFeedbacks() {
        given(feedbackService.findAll()).willReturn(feedbackDtos);
        ResponseEntity<List<FeedbackDto>> response = feedbackRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_shouldReturnFeedbackWithGivenId() {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto1);
        ResponseEntity<FeedbackDto> response = feedbackRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void save_shouldAddFeedbackToList() {
        given(feedbackService.save(any(FeedbackDto.class))).willReturn(feedbackDto1);
        ResponseEntity<FeedbackDto> response = feedbackRestController.save(feedbackDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void updateById_shouldUpdateFeedbackWithGivenId() {
        FeedbackDto feedbackDto = feedbackDto2; feedbackDto.setId(VALID_ID);
        given(feedbackService.updateById(any(FeedbackDto.class), anyInt())).willReturn(feedbackDto);
        ResponseEntity<FeedbackDto> response = feedbackRestController.updateById(feedbackDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDto);
    }

    @Test
    void deleteById_shouldRemoveFeedbackWithGivenIdFromList() {
        ResponseEntity<Void> response = feedbackRestController.deleteById(VALID_ID);
        verify(feedbackService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @ParameterizedTest
    @CsvSource({ "1, ${FEEDBACK_FILTER_KEY}", "2, ${FEEDBACK_FILTER_KEY}", "3, ${FEEDBACK_FILTER_KEY}", "1, ''", "2, ''", "3, ''"  })
    void findAllByKey_shouldReturnListOfFeedbacksFilteredByKey(int page, String key) {
        Pair<List<FeedbackDto>, Pageable> pair = switch(page) {
            case 1 -> Pair.of(getMockedFeedbackDtosPage1(), pageable);
            case 2 -> Pair.of(getMockedFeedbackDtosPage2(), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : getMockedFeedbackDtosPage3(), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
        Page<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(pair.getLeft());
        given(feedbackService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pair.getRight(), key);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }
}
