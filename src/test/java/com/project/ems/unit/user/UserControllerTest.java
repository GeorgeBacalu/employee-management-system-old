package com.project.ems.unit.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToDtoList;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUsers;
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

    @Mock
    private RoleService roleService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private User user;
    private List<User> users;
    private UserDto userDto;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user = getMockedUser1();
        users = getMockedUsers();
        userDto = convertToDto(modelMapper, user);
        userDtos = convertToDtoList(modelMapper, users);
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() {
        given(userService.findAll()).willReturn(userDtos);
        given(model.getAttribute(anyString())).willReturn(users);
        String viewName = userController.getAllUsersPage(model);
        assertThat(viewName).isEqualTo(USERS_VIEW);
        assertThat(model.getAttribute("users")).isEqualTo(users);
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
        String viewName = userController.deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_USERS_VIEW);
        verify(userService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> userController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
