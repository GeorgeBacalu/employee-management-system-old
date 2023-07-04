package com.project.ems.integration.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserController;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.USERS;
import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private ModelMapper modelMapper;

    private User user1;
    private User user2;
    private List<User> users;
    private Role role1;
    private Role role2;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        userDto1 = convertToDto(user1);
        userDto2 = convertToDto(user2);
        userDtos = List.of(userDto1, userDto2);

        given(modelMapper.map(userDto1, User.class)).willReturn(user1);
        given(modelMapper.map(userDto2, User.class)).willReturn(user2);
        given(roleService.findEntityById(userDto1.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(userDto2.getRoleId())).willReturn(role2);
    }

    @Test
    void getAllUsersPage_shouldReturnUsersPage() throws Exception {
        given(userService.findAll()).willReturn(userDtos);
        mockMvc.perform(get(USERS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USERS_VIEW))
              .andExpect(model().attribute("users", users));
        verify(userService).findAll();
    }

    @Test
    void getUserByIdPage_withValidId_shouldReturnUserDetailsPage() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto1);
        mockMvc.perform(get(USERS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(USER_DETAILS_VIEW))
              .andExpect(model().attribute("user", user1));
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
        given(userService.findById(anyInt())).willReturn(userDto1);
        mockMvc.perform(get(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_USER_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("userDto", userDto1));
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
                    .params(convertToMultiValueMap(userDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
        verify(userService).save(any(UserDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateUserWithGivenId() throws Exception {
        mockMvc.perform(post(USERS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
        verify(userService).updateById(userDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(USERS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(userDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).updateById(any(UserDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() throws Exception {
        mockMvc.perform(get(USERS + "/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_USERS_VIEW))
              .andExpect(redirectedUrl(USERS));
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

    private UserDto convertToDto(User user) {
        return UserDto.builder()
              .id(user.getId())
              .name(user.getName())
              .email(user.getEmail())
              .password(user.getPassword())
              .mobile(user.getMobile())
              .address(user.getAddress())
              .birthday(user.getBirthday())
              .roleId(user.getRole().getId())
              .build();
    }
}
