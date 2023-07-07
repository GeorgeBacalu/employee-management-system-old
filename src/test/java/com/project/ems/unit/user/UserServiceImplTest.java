package com.project.ems.unit.user;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToDtoList;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static com.project.ems.mock.UserMock.getMockedUsersPage2;
import static com.project.ems.mock.UserMock.getMockedUsersPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user1;
    private User user2;
    private List<User> users;
    private List<User> usersPage1;
    private List<User> usersPage2;
    private List<User> usersPage3;
    private Role role;
    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;
    private List<UserDto> userDtosPage1;
    private List<UserDto> userDtosPage2;
    private List<UserDto> userDtosPage3;

    @BeforeEach
    void setUp() {
        user1 = getMockedUser1();
        user2 = getMockedUser2();
        users = getMockedUsers();
        usersPage1 = getMockedUsersPage1();
        usersPage2 = getMockedUsersPage2();
        usersPage3 = getMockedUsersPage3();
        role = getMockedRole2();
        userDto1 = convertToDto(modelMapper, user1);
        userDto2 = convertToDto(modelMapper, user2);
        userDtos = convertToDtoList(modelMapper, users);
        userDtosPage1 = convertToDtoList(modelMapper, usersPage1);
        userDtosPage2 = convertToDtoList(modelMapper, usersPage2);
        userDtosPage3 = convertToDtoList(modelMapper, usersPage3);
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
        assertThat(result).isEqualTo(convertToDto(modelMapper, user1));
    }

    @Test
    void updateById_withValidId_shouldUpdateUserWithGivenId() {
        User user = user2; user.setId(VALID_ID);
        given(userRepository.findById(anyInt())).willReturn(Optional.ofNullable(user1));
        given(roleService.findEntityById(anyInt())).willReturn(role);
        given(userRepository.save(any(User.class))).willReturn(user);
        UserDto result = userService.updateById(userDto2, VALID_ID);
        verify(userRepository).save(userCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, userCaptor.getValue()));
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

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfUsersFilteredByKeyPage1() {
        given(userRepository.findAllByKey(pageable, USER_FILTER_KEY)).willReturn(new PageImpl<>(usersPage1));
        Page<UserDto> result = userService.findAllByKey(pageable, USER_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(userDtosPage1);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfUsersFilteredByKeyPage2() {
        given(userRepository.findAllByKey(pageable2, USER_FILTER_KEY)).willReturn(new PageImpl<>(usersPage2));
        Page<UserDto> result = userService.findAllByKey(pageable2, USER_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(userDtosPage2);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfUsersFilteredByKeyPage3() {
        given(userRepository.findAllByKey(pageable3, USER_FILTER_KEY)).willReturn(new PageImpl<>(Collections.emptyList()));
        Page<UserDto> result = userService.findAllByKey(pageable3, USER_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(Collections.emptyList());
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfUsersPage1() {
        given(userRepository.findAll(pageable)).willReturn(new PageImpl<>(usersPage1));
        Page<UserDto> result = userService.findAllByKey(pageable, "");
        assertThat(result.getContent()).isEqualTo(userDtosPage1);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfUsersPage2() {
        given(userRepository.findAll(pageable2)).willReturn(new PageImpl<>(usersPage2));
        Page<UserDto> result = userService.findAllByKey(pageable2, "");
        assertThat(result.getContent()).isEqualTo(userDtosPage2);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfUsersPage3() {
        given(userRepository.findAll(pageable3)).willReturn(new PageImpl<>(usersPage3));
        Page<UserDto> result = userService.findAllByKey(pageable3, "");
        assertThat(result.getContent()).isEqualTo(userDtosPage3);
    }
}
