package com.project.ems.integration.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRepository;
import com.project.ems.user.UserServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUserDto1;
import static com.project.ems.mock.UserMock.getMockedUserDto2;
import static com.project.ems.mock.UserMock.getMockedUserDtosPage1;
import static com.project.ems.mock.UserMock.getMockedUserDtosPage2;
import static com.project.ems.mock.UserMock.getMockedUserDtosPage3;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static com.project.ems.mock.UserMock.getMockedUsersPage2;
import static com.project.ems.mock.UserMock.getMockedUsersPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user1;
    private User user2;
    private List<User> users;
    private Role role;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        role = getMockedRole2();
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = userService.convertToDtos(users);
    }

    @Test
    void findAll_shouldReturnListOfUsers() {
        given(userRepository.findAll()).willReturn(users);
        List<UserDto> result = userService.findAll();
        assertThat(result).isEqualTo(userDtos);
    }

    @Test
    void findById_withValidId_shouldReturnUserWithGivenId() {
        given(userRepository.findById(anyInt())).willReturn(Optional.ofNullable(user1));
        UserDto result = userService.findById(VALID_ID);
        assertThat(result).isEqualTo(userDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> userService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddUserToList() {
        given(userRepository.save(any(User.class))).willReturn(user1);
        UserDto result = userService.save(userDto1);
        verify(userRepository).save(userCaptor.capture());
        assertThat(result).isEqualTo(userService.convertToDto(user1));
    }

    @Test
    void updateById_withValidId_shouldUpdateUserWithGivenId() {
        User user = user2; user.setId(VALID_ID);
        given(userRepository.findById(anyInt())).willReturn(Optional.ofNullable(user1));
        given(roleService.findEntityById(anyInt())).willReturn(role);
        given(userRepository.save(any(User.class))).willReturn(user);
        UserDto result = userService.updateById(userDto2, VALID_ID);
        verify(userRepository).save(userCaptor.capture());
        assertThat(result).isEqualTo(userService.convertToDto(userCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> userService.updateById(userDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() {
        given(userRepository.findById(anyInt())).willReturn(Optional.ofNullable(user1));
        userService.deleteById(VALID_ID);
        verify(userRepository).delete(user1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> userService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(USER_NOT_FOUND, INVALID_ID));
        verify(userRepository, never()).delete(any(User.class));
    }

    private Stream<Arguments> paginationArguments() {
        Page<User> usersPage1 = new PageImpl<>(getMockedUsersPage1());
        Page<User> usersPage2 = new PageImpl<>(getMockedUsersPage2());
        Page<User> usersPage3 = new PageImpl<>(getMockedUsersPage3());
        Page<User> emptyPage = new PageImpl<>(Collections.emptyList());
        Page<UserDto> userDtosPage1 = new PageImpl<>(getMockedUserDtosPage1());
        Page<UserDto> userDtosPage2 = new PageImpl<>(getMockedUserDtosPage2());
        Page<UserDto> userDtosPage3 = new PageImpl<>(getMockedUserDtosPage3());
        Page<UserDto> emptyDtoPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", USER_FILTER_KEY, usersPage1, userDtosPage1),
                         Arguments.of(1, 2, "id", USER_FILTER_KEY, usersPage2, userDtosPage2),
                         Arguments.of(2, 2, "id", USER_FILTER_KEY, emptyPage, emptyDtoPage),
                         Arguments.of(0, 2, "id", "", usersPage1, userDtosPage1),
                         Arguments.of(1, 2, "id", "", usersPage2, userDtosPage2),
                         Arguments.of(2, 2, "id", "", usersPage3, userDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String key, Page<User> entityPage, Page<UserDto> dtoPage) {
        if(key.trim().equals("")) {
            given(userRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(userRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(entityPage);
        }
        Page<UserDto> result = userService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
