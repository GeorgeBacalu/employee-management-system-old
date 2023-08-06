package com.project.ems.integration.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackController;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.feedback.FeedbackService;
import com.project.ems.user.User;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
import static com.project.ems.mock.FeedbackMock.getMockedFeedback10;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback11;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback12;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback3;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback4;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback5;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback6;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback7;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback8;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback9;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser10;
import static com.project.ems.mock.UserMock.getMockedUser11;
import static com.project.ems.mock.UserMock.getMockedUser12;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUser3;
import static com.project.ems.mock.UserMock.getMockedUser4;
import static com.project.ems.mock.UserMock.getMockedUser5;
import static com.project.ems.mock.UserMock.getMockedUser6;
import static com.project.ems.mock.UserMock.getMockedUser7;
import static com.project.ems.mock.UserMock.getMockedUser8;
import static com.project.ems.mock.UserMock.getMockedUser9;
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

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    private Feedback feedback1;
    private Feedback feedback2;
    private Feedback feedback3;
    private Feedback feedback4;
    private Feedback feedback5;
    private Feedback feedback6;
    private Feedback feedback7;
    private Feedback feedback8;
    private Feedback feedback9;
    private Feedback feedback10;
    private Feedback feedback11;
    private Feedback feedback12;
    private List<Feedback> feedbacks;
    private List<Feedback> feedbacksFirstPage;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;
    private User user9;
    private User user10;
    private User user11;
    private User user12;
    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private FeedbackDto feedbackDto3;
    private FeedbackDto feedbackDto4;
    private FeedbackDto feedbackDto5;
    private FeedbackDto feedbackDto6;
    private FeedbackDto feedbackDto7;
    private FeedbackDto feedbackDto8;
    private FeedbackDto feedbackDto9;
    private FeedbackDto feedbackDto10;
    private FeedbackDto feedbackDto11;
    private FeedbackDto feedbackDto12;
    private List<FeedbackDto> feedbackDtos;
    private List<FeedbackDto> feedbackDtosFirstPage;

    @BeforeEach
    void setUp() {
        feedback1 = getMockedFeedback1();
        feedback2 = getMockedFeedback2();
        feedback3 = getMockedFeedback3();
        feedback4 = getMockedFeedback4();
        feedback5 = getMockedFeedback5();
        feedback6 = getMockedFeedback6();
        feedback7 = getMockedFeedback7();
        feedback8 = getMockedFeedback8();
        feedback9 = getMockedFeedback9();
        feedback10 = getMockedFeedback10();
        feedback11 = getMockedFeedback11();
        feedback12 = getMockedFeedback12();
        feedbacks = getMockedFeedbacks();
        feedbacksFirstPage = List.of(feedback1, feedback2, feedback3, feedback4, feedback5, feedback6, feedback7, feedback8, feedback9, feedback10);
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        user3 = getMockedUser3();
        user4 = getMockedUser4();
        user5 = getMockedUser5();
        user6 = getMockedUser6();
        user7 = getMockedUser7();
        user8 = getMockedUser8();
        user9 = getMockedUser9();
        user10 = getMockedUser10();
        user11 = getMockedUser11();
        user12 = getMockedUser12();
        feedbackDto1 = convertToDto(feedback1);
        feedbackDto2 = convertToDto(feedback2);
        feedbackDto3 = convertToDto(feedback3);
        feedbackDto4 = convertToDto(feedback4);
        feedbackDto5 = convertToDto(feedback5);
        feedbackDto6 = convertToDto(feedback6);
        feedbackDto7 = convertToDto(feedback7);
        feedbackDto8 = convertToDto(feedback8);
        feedbackDto9 = convertToDto(feedback9);
        feedbackDto10 = convertToDto(feedback10);
        feedbackDto11 = convertToDto(feedback11);
        feedbackDto12 = convertToDto(feedback12);
        feedbackDtos = List.of(feedbackDto1, feedbackDto2, feedbackDto3, feedbackDto4, feedbackDto5, feedbackDto6, feedbackDto7, feedbackDto8, feedbackDto9, feedbackDto10, feedbackDto11, feedbackDto12);
        feedbackDtosFirstPage = List.of(feedbackDto1, feedbackDto2, feedbackDto3, feedbackDto4, feedbackDto5, feedbackDto6, feedbackDto7, feedbackDto8, feedbackDto9, feedbackDto10);

        given(modelMapper.map(feedbackDto1, Feedback.class)).willReturn(feedback1);
        given(modelMapper.map(feedbackDto2, Feedback.class)).willReturn(feedback2);
        given(modelMapper.map(feedbackDto3, Feedback.class)).willReturn(feedback3);
        given(modelMapper.map(feedbackDto4, Feedback.class)).willReturn(feedback4);
        given(modelMapper.map(feedbackDto5, Feedback.class)).willReturn(feedback5);
        given(modelMapper.map(feedbackDto6, Feedback.class)).willReturn(feedback6);
        given(modelMapper.map(feedbackDto7, Feedback.class)).willReturn(feedback7);
        given(modelMapper.map(feedbackDto8, Feedback.class)).willReturn(feedback8);
        given(modelMapper.map(feedbackDto9, Feedback.class)).willReturn(feedback9);
        given(modelMapper.map(feedbackDto10, Feedback.class)).willReturn(feedback10);
        given(modelMapper.map(feedbackDto11, Feedback.class)).willReturn(feedback11);
        given(modelMapper.map(feedbackDto12, Feedback.class)).willReturn(feedback12);

        given(userService.findEntityById(feedbackDto1.getUserId())).willReturn(user1);
        given(userService.findEntityById(feedbackDto2.getUserId())).willReturn(user2);
        given(userService.findEntityById(feedbackDto3.getUserId())).willReturn(user3);
        given(userService.findEntityById(feedbackDto4.getUserId())).willReturn(user4);
        given(userService.findEntityById(feedbackDto5.getUserId())).willReturn(user5);
        given(userService.findEntityById(feedbackDto6.getUserId())).willReturn(user6);
        given(userService.findEntityById(feedbackDto7.getUserId())).willReturn(user7);
        given(userService.findEntityById(feedbackDto8.getUserId())).willReturn(user8);
        given(userService.findEntityById(feedbackDto9.getUserId())).willReturn(user9);
        given(userService.findEntityById(feedbackDto10.getUserId())).willReturn(user10);
        given(userService.findEntityById(feedbackDto11.getUserId())).willReturn(user11);
        given(userService.findEntityById(feedbackDto12.getUserId())).willReturn(user12);
    }

    @Test
    void getAllFeedbacksPage_shouldReturnFeedbacksPage() throws Exception {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosFirstPage);
        given(feedbackService.findAllByKey(any(Pageable.class), anyString())).willReturn(feedbackDtosPage);
        int page = pageable.getPageNumber();
        int size = feedbackDtosFirstPage.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrFeedbacks = feedbackDtosPage.getTotalElements();
        int nrPages = feedbackDtosPage.getTotalPages();
        mockMvc.perform(get(FEEDBACKS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACKS_VIEW))
              .andExpect(model().attribute("feedbacks", feedbacksFirstPage))
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
                    .param("size", String.valueOf(feedbackDtosFirstPage.size()))
                    .param("key", FEEDBACK_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(FEEDBACKS + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getFeedbackByIdPage_withValidId_shouldReturnFeedbackDetailsPage() throws Exception {
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto1);
        mockMvc.perform(get(FEEDBACKS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(FEEDBACK_DETAILS_VIEW))
              .andExpect(model().attribute("feedback", feedback1));
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
        given(feedbackService.findById(anyInt())).willReturn(feedbackDto1);
        mockMvc.perform(get(FEEDBACKS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_FEEDBACK_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("feedbackDto", feedbackDto1));
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
                    .params(convertToMultiValueMap(feedbackDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl(FEEDBACKS));
        verify(feedbackService).save(any(FeedbackDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateFeedbackWithGivenId() throws Exception {
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(feedbackDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_FEEDBACKS_VIEW))
              .andExpect(redirectedUrl(FEEDBACKS));
        verify(feedbackService).updateById(feedbackDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(FEEDBACK_NOT_FOUND, INVALID_ID);
        given(feedbackService.updateById(any(FeedbackDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(FEEDBACKS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(feedbackDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(feedbackService).updateById(any(FeedbackDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() throws Exception {
        PageImpl<FeedbackDto> feedbackDtosPage = new PageImpl<>(feedbackDtosFirstPage);
        given(feedbackService.findAllByKey(any(Pageable.class), anyString())).willReturn(feedbackDtosPage);
        mockMvc.perform(get(FEEDBACKS + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(feedbackDtosFirstPage.size()))
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
        return params;
    }

    private FeedbackDto convertToDto(Feedback feedback) {
        return FeedbackDto.builder()
              .id(feedback.getId())
              .type(feedback.getType())
              .description(feedback.getDescription())
              .userId(feedback.getUser().getId())
              .build();
    }
}
