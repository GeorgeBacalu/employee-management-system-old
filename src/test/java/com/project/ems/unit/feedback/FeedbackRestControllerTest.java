package com.project.ems.unit.feedback;

import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackRestController;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedbackRestControllerTest {

    @InjectMocks
    private FeedbackRestController feedbackRestController;

    @Mock
    private FeedbackService feedbackService;

    @Spy
    private ModelMapper modelMapper;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;
    private List<FeedbackDto> feedbackDtosPage1;
    private List<FeedbackDto> feedbackDtosPage2;
    private List<FeedbackDto> feedbackDtosPage3;

    @BeforeEach
    void setUp() {
        feedbackDto1 = convertToDto(modelMapper, getMockedFeedback1());
        feedbackDto2 = convertToDto(modelMapper, getMockedFeedback2());
        feedbackDtos = convertToDtoList(modelMapper, getMockedFeedbacks());
        feedbackDtosPage1 = convertToDtoList(modelMapper, getMockedFeedbacksPage1());
        feedbackDtosPage2 = convertToDtoList(modelMapper, getMockedFeedbacksPage2());
        feedbackDtosPage3 = convertToDtoList(modelMapper, getMockedFeedbacksPage3());
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

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfFeedbacksFilteredByKeyPage1() {
        PageImpl<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        given(feedbackService.findAllByKey(pageable, FEEDBACK_FILTER_KEY)).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageable, FEEDBACK_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfFeedbacksFilteredByKeyPage2() {
        PageImpl<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(feedbackDtosPage2);
        given(feedbackService.findAllByKey(pageable2, FEEDBACK_FILTER_KEY)).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageable2, FEEDBACK_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfFeedbacksFilteredByKeyPage3() {
        PageImpl<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(Collections.emptyList());
        given(feedbackService.findAllByKey(pageable3, FEEDBACK_FILTER_KEY)).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageable3, FEEDBACK_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfFeedbacksPage1() {
        PageImpl<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        given(feedbackService.findAllByKey(pageable, "")).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageable, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfFeedbacksPage2() {
        PageImpl<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(feedbackDtosPage2);
        given(feedbackService.findAllByKey(pageable2, "")).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageable2, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfFeedbacksPage3() {
        PageImpl<FeedbackDto> filteredFeedbackDtosPage = new PageImpl<>(feedbackDtosPage3);
        given(feedbackService.findAllByKey(pageable3, "")).willReturn(filteredFeedbackDtosPage);
        ResponseEntity<PageWrapper<FeedbackDto>> response = feedbackRestController.findAllByKey(pageable3, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredFeedbackDtosPage.getContent()));
    }
}
