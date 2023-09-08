package com.project.ems.integration.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
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

import static com.project.ems.constants.EndpointConstants.USERS;
import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUserDto1;
import static com.project.ems.mock.UserMock.getMockedUserDtosFirstPage;
import static com.project.ems.mock.UserMock.getMockedUsersFirstPage;
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

@WebMvcTest(UserController.class)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private List<User> usersPage1;
    private UserDto userDto;
    private List<UserDto> userDtosPage1;

    @BeforeEach
    void setUp() {
        user = getMockedUser1();
        usersPage1 = getMockedUsersFirstPage();
        userDto = getMockedUserDto1();
        userDtosPage1 = getMockedUserDtosFirstPage();
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() throws Exception {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtosPage1);
        given(userService.findAllByKey(any(Pageable.class), anyString())).willReturn(userDtosPage);
        given(userService.convertToEntities(userDtosPage.getContent())).willReturn(usersPage1);
        int page = pageable.getPageNumber();
        int size = userDtosPage1.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        mockMvc.perform(get(USERS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USERS_VIEW))
              .andExpect(model().attribute("users", usersPage1))
              .andExpect(model().attribute("nrUsers", nrUsers))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size)))
              .andExpect(model().attribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrUsers)))
              .andExpect(model().attribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction)));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfUsersFilteredByKey() throws Exception {
        mockMvc.perform(post(USERS + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(userDtosPage1.size()))
                    .param("key", USER_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(USERS + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getUserByIdPage_withValidId_shouldReturnUserDetailsPage() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto);
        given(userService.convertToEntity(userDto)).willReturn(user);
        mockMvc.perform(get(USERS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USER_DETAILS_VIEW))
              .andExpect(model().attribute("user", user));
        verify(userService).findById(VALID_ID);
    }

    @Test
    void getUserByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(USERS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).findById(INVALID_ID);
    }

    @Test
    void getSaveUserPage_withNegativeId_shouldReturnSaveUserPage() throws Exception {
        mockMvc.perform(get(USERS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("userDto", new UserDto()));
    }

    @Test
    void getSaveUserPage_withValidId_shouldReturnUpdateUserPage() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto);
        mockMvc.perform(get(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("userDto", userDto));
    }

    @Test
    void getSaveUserPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveUser() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
        verify(userService).save(any(UserDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateUserWithGivenId() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
        verify(userService).updateById(userDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).updateById(any(UserDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() throws Exception {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtosPage1);
        given(userService.findAllByKey(any(Pageable.class), anyString())).willReturn(userDtosPage);
        mockMvc.perform(get(USERS + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(userDtosPage1.size()))
                    .param("key", USER_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(USERS + "?page=*&size=*&key=*&sort=*"));
        verify(userService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).deleteById(anyInt());
        mockMvc.perform(get(USERS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(UserDto userDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", userDto.getName());
        params.add("email", userDto.getEmail());
        params.add("password", userDto.getPassword());
        params.add("mobile", userDto.getMobile());
        params.add("address", userDto.getAddress());
        params.add("birthday", userDto.getBirthday().toString());
        params.add("roleId", userDto.getRoleId().toString());
        return params;
    }
}
