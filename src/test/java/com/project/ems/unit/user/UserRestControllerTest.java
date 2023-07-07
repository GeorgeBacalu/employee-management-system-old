package com.project.ems.unit.user;

import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToDtoList;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static com.project.ems.mock.UserMock.getMockedUsersPage2;
import static com.project.ems.mock.UserMock.getMockedUsersPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;
    private List<UserDto> userDtosPage1;
    private List<UserDto> userDtosPage2;
    private List<UserDto> userDtosPage3;

    @BeforeEach
    void setUp() {
        userDto1 = convertToDto(modelMapper, getMockedUser1());
        userDto2 = convertToDto(modelMapper, getMockedUser2());
        userDtos = convertToDtoList(modelMapper, getMockedUsers());
        userDtosPage1 = convertToDtoList(modelMapper, getMockedUsersPage1());
        userDtosPage2 = convertToDtoList(modelMapper, getMockedUsersPage2());
        userDtosPage3 = convertToDtoList(modelMapper, getMockedUsersPage3());
    }

    @Test
    void findAll_shouldReturnListOfUsers() {
        given(userService.findAll()).willReturn(userDtos);
        ResponseEntity<List<UserDto>> response = userRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDtos);
    }

    @Test
    void findById_shouldReturnUserWithGivenId() {
        given(userService.findById(anyInt())).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void save_shouldAddUserToList() {
        given(userService.save(any(UserDto.class))).willReturn(userDto1);
        ResponseEntity<UserDto> response = userRestController.save(userDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void updateById_shouldUpdateUserWithGivenId() {
        UserDto userDto = userDto2; userDto.setId(VALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willReturn(userDto);
        ResponseEntity<UserDto> response = userRestController.updateById(userDto, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    void deleteById_shouldRemoveUserWithGivenIdFromList() {
        ResponseEntity<Void> response = userRestController.deleteById(VALID_ID);
        verify(userService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfUsersFilteredByKeyPage1() {
        PageImpl<UserDto> filteredUserDtosPage = new PageImpl<>(userDtosPage1);
        given(userService.findAllByKey(pageable, USER_FILTER_KEY)).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageable, USER_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredUserDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfUsersFilteredByKeyPage2() {
        PageImpl<UserDto> filteredUserDtosPage = new PageImpl<>(userDtosPage2);
        given(userService.findAllByKey(pageable2, USER_FILTER_KEY)).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageable2, USER_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredUserDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfUsersFilteredByKeyPage3() {
        PageImpl<UserDto> filteredUserDtosPage = new PageImpl<>(Collections.emptyList());
        given(userService.findAllByKey(pageable3, USER_FILTER_KEY)).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageable3, USER_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredUserDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfUsersPage1() {
        PageImpl<UserDto> filteredUserDtosPage = new PageImpl<>(userDtosPage1);
        given(userService.findAllByKey(pageable, "")).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageable, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredUserDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfUsersPage2() {
        PageImpl<UserDto> filteredUserDtosPage = new PageImpl<>(userDtosPage2);
        given(userService.findAllByKey(pageable2, "")).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageable2, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredUserDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfUsersPage3() {
        PageImpl<UserDto> filteredUserDtosPage = new PageImpl<>(userDtosPage3);
        given(userService.findAllByKey(pageable3, "")).willReturn(filteredUserDtosPage);
        ResponseEntity<PageWrapper<UserDto>> response = userRestController.findAllByKey(pageable3, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredUserDtosPage.getContent()));
    }
}
