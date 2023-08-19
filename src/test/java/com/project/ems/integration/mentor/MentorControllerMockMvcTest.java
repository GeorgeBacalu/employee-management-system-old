package com.project.ems.integration.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

import static com.project.ems.constants.EndpointConstants.MENTORS;
import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_MENTOR_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentorDto1;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosFirstPage;
import static com.project.ems.mock.MentorMock.getMockedMentorsFirstPage;
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

@WebMvcTest(MentorController.class)
class MentorControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MentorService mentorService;

    private Mentor mentor;
    private List<Mentor> mentorsPage1;
    private MentorDto mentorDto;
    private List<MentorDto> mentorDtosPage1;

    @BeforeEach
    void setUp() {
        mentor = getMockedMentor1();
        mentorsPage1 = getMockedMentorsFirstPage();
        mentorDto = getMockedMentorDto1();
        mentorDtosPage1 = getMockedMentorDtosFirstPage();
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() throws Exception {
        PageImpl<MentorDto> mentorDtosPage = new PageImpl<>(mentorDtosPage1);
        given(mentorService.findAllByKey(any(Pageable.class), anyString())).willReturn(mentorDtosPage);
        given(mentorService.convertToEntities(mentorDtosPage.getContent())).willReturn(mentorsPage1);
        int page = pageable.getPageNumber();
        int size = mentorDtosPage1.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrMentors = mentorDtosPage.getTotalElements();
        int nrPages = mentorDtosPage.getTotalPages();
        mockMvc.perform(get(MENTORS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTORS_VIEW))
              .andExpect(model().attribute("mentors", mentorsPage1))
              .andExpect(model().attribute("nrMentors", nrMentors))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size)))
              .andExpect(model().attribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrMentors)))
              .andExpect(model().attribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction)));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfMentorsFilteredByKey() throws Exception {
        mockMvc.perform(post(MENTORS + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(mentorDtosPage1.size()))
                    .param("key", MENTOR_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(MENTORS + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto);
        given(mentorService.convertToEntity(mentorDto)).willReturn(mentor);
        mockMvc.perform(get(MENTORS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTOR_DETAILS_VIEW))
              .andExpect(model().attribute("mentor", mentor));
        verify(mentorService).findById(VALID_ID);
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(MENTORS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).findById(INVALID_ID);
    }

    @Test
    void getSaveMentorPage_withNegativeId_shouldReturnSaveMentorPage() throws Exception {
        mockMvc.perform(get(MENTORS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("mentorDto", new MentorDto()));
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnUpdateMentorPage() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto);
        mockMvc.perform(get(MENTORS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("mentorDto", mentorDto));
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(MENTORS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveMentor() throws Exception {
        mockMvc.perform(post(MENTORS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).save(any(MentorDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateMentorWithGivenId() throws Exception {
        mockMvc.perform(post(MENTORS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).updateById(mentorDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(MENTORS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).updateById(any(MentorDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        PageImpl<MentorDto> mentorDtosPage = new PageImpl<>(mentorDtosPage1);
        given(mentorService.findAllByKey(any(Pageable.class), anyString())).willReturn(mentorDtosPage);
        mockMvc.perform(get(MENTORS + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(mentorDtosPage1.size()))
                    .param("key", MENTOR_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(MENTORS + "?page=*&size=*&key=*&sort=*"));
        verify(mentorService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(anyInt());
        mockMvc.perform(get(MENTORS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(MentorDto mentorDto) {
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", mentorDto.getName());
        params.add("email", mentorDto.getEmail());
        params.add("password", mentorDto.getPassword());
        params.add("mobile", mentorDto.getMobile());
        params.add("address", mentorDto.getAddress());
        params.add("birthday", mentorDto.getBirthday().toString());
        params.add("roleId", mentorDto.getRoleId().toString());
        params.add("employmentType", mentorDto.getEmploymentType().name());
        params.add("position", mentorDto.getPosition().name());
        params.add("grade", mentorDto.getGrade().name());
        params.add("supervisingMentorId", supervisingMentorId != null ? mentorDto.getSupervisingMentorId().toString() : null);
        params.add("studiesIds", mentorDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("experiencesIds", mentorDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("nrTrainees", mentorDto.getNrTrainees().toString());
        params.add("maxTrainees", mentorDto.getMaxTrainees().toString());
        params.add("isTrainingOpen", mentorDto.getIsTrainingOpen().toString());
        return params;
    }
}
