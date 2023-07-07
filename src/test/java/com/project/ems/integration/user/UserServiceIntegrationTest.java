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
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
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

    @Spy
    private ModelMapper modelMapper;

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
        userDto1 = convertToDto(modelMapper, user1);
        userDto2 = convertToDto(modelMapper, user2);
        userDtos = convertToDtoList(modelMapper, users);
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

    private Stream<Arguments> paginationArguments() {
        List<User> usersPage1 = getMockedUsersPage1();
        List<User> usersPage2 = getMockedUsersPage2();
        List<User> usersPage3 = getMockedUsersPage3();
        List<UserDto> userDtosPage1 = convertToDtoList(modelMapper, usersPage1);
        List<UserDto> userDtosPage2 = convertToDtoList(modelMapper, usersPage2);
        List<UserDto> userDtosPage3 = convertToDtoList(modelMapper, usersPage3);
        return Stream.of(Arguments.of(0, 2, "id", "asc", USER_FILTER_KEY, new PageImpl<>(usersPage1), new PageImpl<>(userDtosPage1)),
              Arguments.of(1, 2, "id", "asc", USER_FILTER_KEY, new PageImpl<>(usersPage2), new PageImpl<>(userDtosPage2)),
              Arguments.of(2, 2, "id", "asc", USER_FILTER_KEY, new PageImpl<>(Collections.emptyList()), new PageImpl<>(Collections.emptyList())),
              Arguments.of(0, 2, "id", "asc", "", new PageImpl<>(usersPage1), new PageImpl<>(userDtosPage1)),
              Arguments.of(1, 2, "id", "asc", "", new PageImpl<>(usersPage2), new PageImpl<>(userDtosPage2)),
              Arguments.of(2, 2, "id", "asc", "", new PageImpl<>(usersPage3), new PageImpl<>(userDtosPage3)));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, PageImpl<User> entityPage, PageImpl<UserDto> dtoPage) {
        if(key.trim().equals("")) {
            given(userRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(userRepository.findAllByKey(any(Pageable.class), anyString())).willReturn(entityPage);
        }
        Page<UserDto> result = userService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
