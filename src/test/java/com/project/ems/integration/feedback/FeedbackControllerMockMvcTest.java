package com.project.ems.integration.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackController;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackService;
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

import static com.project.ems.constants.EndpointConstants.FEEDBACKS;
import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.FEEDBACK_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_FEEDBACKS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_FEEDBACK_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDto1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbackDtosFirstPage;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksFirstPage;
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

@WebMvcTest(FeedbackController.class)
class FeedbackControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    private Feedback feedback;
    private List<Feedback> feedbacksPage1;
    private FeedbackDto feedbackDto;
    private List<FeedbackDto> feedbackDtosPage1;

    @BeforeEach
    void setUp() {
        feedback = getMockedFeedback1();
        feedbacksPage1 = getMockedFeedbacksFirstPage();
        feedbackDto = getMockedFeedbackDto1();
        feedbackDtosPage1 = getMockedFeedbackDtosFirstPage();
    }

    @Test
    void getAllFeedbacksPage_shouldReturnFeedbacksPage() throws Exception {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        given(feedbackService.findAllByKey(any(Pageable.class), anyString())).willReturn(feedbackDtosPage);
        given(feedbackService.convertToEntities(feedbackDtosPage.getContent())).willReturn(feedbacksPage1);
        int page = pageable.getPageNumber();
        int size = feedbackDtosPage1.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        mockMvc.perform(get(FEEDBACKS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACKS_VIEW))
              .andExpect(model().attribute("feedbacks", feedbacksPage1))
              .andExpect(model().attribute("nrFeedbacks", nrFeedbacks))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size)))
              .andExpect(model().attribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrFeedbacks)))
              .andExpect(model().attribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction)));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfFeedbacksFilteredByKey() throws Exception {
        mockMvc.perform(post(FEEDBACKS + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(feedbackDtosPage1.size()))
                    .param("key", FEEDBACK_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(FEEDBACKS + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getFeedbackByIdPage_withValidId_shouldReturnFeedbackDetailsPage() throws Exception {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto);
        given(feedbackService.convertToEntity(feedbackDto)).willReturn(feedback);
        mockMvc.perform(get(FEEDBACKS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACK_DETAILS_VIEW))
              .andExpect(model().attribute("feedback", feedback));
        verify(feedbackService).findById(VALID_ID);
    }

    @Test
    void getFeedbackByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(FEEDBACKS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).findById(INVALID_ID);
    }

    @Test
    void getSaveFeedbackPage_withNegativeId_shouldReturnSaveFeedbackPage() throws Exception {
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("feedbackDto", new FeedbackDto()));
    }

    @Test
    void getSaveFeedbackPage_withValidId_shouldReturnUpdateFeedbackPage() throws Exception {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto);
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("feedbackDto", feedbackDto));
    }

    @Test
    void getSaveFeedbackPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveFeedback() throws Exception {
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(feedbackDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl(FEEDBACKS));
        verify(feedbackService).save(any(FeedbackDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateFeedbackWithGivenId() throws Exception {
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(feedbackDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl(FEEDBACKS));
        verify(feedbackService).updateById(feedbackDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(any(FeedbackDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(feedbackDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).updateById(any(FeedbackDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() throws Exception {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosPage1);
        given(feedbackService.findAllByKey(any(Pageable.class), anyString())).willReturn(feedbackDtosPage);
        mockMvc.perform(get(FEEDBACKS + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(feedbackDtosPage1.size()))
                    .param("key", FEEDBACK_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(FEEDBACKS + "?page=*&size=*&key=*&sort=*"));
        verify(feedbackService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(feedbackService).deleteById(anyInt());
        mockMvc.perform(get(FEEDBACKS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(FeedbackDto feedbackDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", feedbackDto.getType().name());
        params.add("description", feedbackDto.getDescription());
        params.add("userId", feedbackDto.getUserId().toString());
        params.add("sentAt", feedbackDto.getSentAt().toString());
        return params;
    }
}
