package com.project.ems.unit.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUserDto1;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;

    private User user;
    private List<User> users;
    private UserDto userDto;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user = getMockedUser1();
        users = getMockedUsersPage1();
        userDto = getMockedUserDto1();
        userDtos = userService.convertToDtos(users);
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtos);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        given(userService.findAllByKey(pageable, USER_FILTER_KEY)).willReturn(userDtosPage);
        given(model.getAttribute("users")).willReturn(users);
        given(model.getAttribute("nrUsers")).willReturn(nrUsers);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("key")).willReturn(USER_FILTER_KEY);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("startIndexCurrentPage")).willReturn(getStartIndexCurrentPage(page, size));
        given(model.getAttribute("endIndexCurrentPage")).willReturn(getEndIndexCurrentPage(page, size, nrUsers));
        given(model.getAttribute("startIndexPageNavigation")).willReturn(getStartIndexPageNavigation(page, nrPages));
        given(model.getAttribute("endIndexPageNavigation")).willReturn(getEndIndexPageNavigation(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = userController.getAllUsersPage(model, pageable, USER_FILTER_KEY);
        assertThat(viewName).isEqualTo(USERS_VIEW);
        assertThat(model.getAttribute("users")).isEqualTo(users);
        assertThat(model.getAttribute("nrUsers")).isEqualTo(nrUsers);
        assertThat(model.getAttribute("nrPages")).isEqualTo(nrPages);
        assertThat(model.getAttribute("page")).isEqualTo(page);
        assertThat(model.getAttribute("size")).isEqualTo(size);
        assertThat(model.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
        assertThat(model.getAttribute("field")).isEqualTo(field);
        assertThat(model.getAttribute("direction")).isEqualTo(direction);
        assertThat(model.getAttribute("startIndexCurrentPage")).isEqualTo(getStartIndexCurrentPage(page, size));
        assertThat(model.getAttribute("endIndexCurrentPage")).isEqualTo(getEndIndexCurrentPage(page, size, nrUsers));
        assertThat(model.getAttribute("startIndexPageNavigation")).isEqualTo(getStartIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("endIndexPageNavigation")).isEqualTo(getEndIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfUsersFilteredByKey() {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtos);
        int page = userDtosPage.getNumber();
        int size = userDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(USER_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = userController.findAllByKey(new SearchRequest(page, size, USER_FILTER_KEY, sort), redirectAttributes);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
    }

    @Test
    void getUserByIdPage_withValidId_shouldReturnUserDetailsPage() {
        given(userService.findById(anyInt())).willReturn(userDto);
        given(model.getAttribute(anyString())).willReturn(user);
        String viewName = userController.getUserByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(USER_DETAILS_VIEW);
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    void getUserByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> userController.getUserByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveUserPage_withNegativeId_shouldReturnSaveUserPage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("userDto")).willReturn(new UserDto());
        String viewName = userController.getSaveUserPage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_USER_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("userDto")).isEqualTo(new UserDto());
    }

    @Test
    void getSaveUserPage_withValidId_shouldReturnUpdateUserPage() {
        given(userService.findById(anyInt())).willReturn(userDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("userDto")).willReturn(userDto);
        String viewName = userController.getSaveUserPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_USER_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("userDto")).isEqualTo(userDto);
    }

    @Test
    void getSaveUserPage_withInvalidId_shouldThrowException() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> userController.getSaveUserPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveUser() {
        String viewName = userController.save(userDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        verify(userService).save(userDto);
    }

    @Test
    void save_withValidId_shouldUpdateUserWithGivenId() {
        String viewName = userController.save(userDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        verify(userService).updateById(userDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(userDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> userController.save(userDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() {
        PageImpl<UserDto> userDtosPage = new PageImpl<>(userDtos);
        given(userService.findAllByKey(pageable, USER_FILTER_KEY)).willReturn(userDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(userDtosPage.getNumber());
        given(redirectAttributes.getAttribute("size")).willReturn(userDtosPage.getSize());
        given(redirectAttributes.getAttribute("key")).willReturn(USER_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(getSortField(pageable) + ',' +  getSortDirection(pageable));
        String viewName = userController.deleteById(VALID_ID, redirectAttributes, pageable, USER_FILTER_KEY);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        verify(userService).deleteById(VALID_ID);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(userDtosPage.getNumber());
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(userDtosPage.getSize());
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(USER_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(getSortField(pageable) + ',' +  getSortDirection(pageable));
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> userController.deleteById(INVALID_ID, redirectAttributes, pageable, USER_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
