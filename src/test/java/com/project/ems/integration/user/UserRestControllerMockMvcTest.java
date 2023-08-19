package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_PAGINATION;
import static com.project.ems.constants.EndpointConstants.API_USERS;
import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.mock.UserMock.getMockedUserDto1;
import static com.project.ems.mock.UserMock.getMockedUserDto2;
import static com.project.ems.mock.UserMock.getMockedUserDtosPage1;
import static com.project.ems.mock.UserMock.getMockedUserDtosPage2;
import static com.project.ems.mock.UserMock.getMockedUserDtosPage3;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(UserRestController.class)
@ExtendWith(MockitoExtension.class)
class UserRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        userDto1 = getMockedUserDto1();
        userDto2 = getMockedUserDto2();
        userDtos = userService.convertToDtos(getMockedUsers());
    }

    @Test
    void findAll_shouldReturnListOfUsers() throws Exception {
        given(userService.findAll()).willReturn(userDtos);
        ResultActions actions = mockMvc.perform(get(API_USERS)).andExpect(status().isOk());
        for(int i = 0; i < userDtos.size(); i++) {
           assertUserDto(actions, "$[" + i + "]", userDtos.get(i));
        }
        List<UserDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(userDtos);
    }

    @Test
    void findById_withValidId_shouldReturnUserWithGivenId() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto1);
        ResultActions actions = mockMvc.perform(get(API_USERS + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertUserDtoJson(actions, userDto1);
        UserDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto1);
        verify(userService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_USERS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddUserToList() throws Exception {
        given(userService.save(any(UserDto.class))).willReturn(userDto1);
        ResultActions actions = mockMvc.perform(post(API_USERS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto1)))
              .andExpect(status().isCreated());
        assertUserDtoJson(actions, userDto1);
        UserDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto1);
        verify(userService).save(userDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateUserWithGivenId() throws Exception {
        UserDto userDto = userDto2; userDto.setId(VALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willReturn(userDto);
        ResultActions actions = mockMvc.perform(put(API_USERS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto2)))
              .andExpect(status().isOk());
        assertUserDtoJson(actions, userDto);
        UserDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto);
        verify(userService).updateById(userDto2, VALID_ID);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_USERS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).updateById(userDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_USERS + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(userService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(USER_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(userService).deleteById(anyInt());
        mockMvc.perform(delete(API_USERS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(userService).deleteById(INVALID_ID);
    }

    private Stream<Arguments> paginationArguments() {
        Page<UserDto> userDtosPage1 = new PageImpl<>(getMockedUserDtosPage1());
        Page<UserDto> userDtosPage2 = new PageImpl<>(getMockedUserDtosPage2());
        Page<UserDto> userDtosPage3 = new PageImpl<>(getMockedUserDtosPage3());
        Page<EmployeeDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", USER_FILTER_KEY, userDtosPage1),
                         Arguments.of(1, 2, "id", "asc", USER_FILTER_KEY, userDtosPage2),
                         Arguments.of(2, 2, "id", "asc", USER_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", userDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", userDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", userDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<UserDto> expectedPage) throws Exception {
        given(userService.findAllByKey(any(Pageable.class), anyString())).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_USERS + API_PAGINATION, page, size, sortField, sortDirection, key)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(int i = 0; i < expectedPage.getContent().size(); i++) {
            assertUserDto(actions, "$.content[" + i + "]", expectedPage.getContent().get(i));
        }
        PageWrapper<UserDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response.getContent()).isEqualTo(expectedPage.getContent());
    }

    private void assertUserDto(ResultActions actions, String prefix, UserDto userDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(userDto.getId()))
              .andExpect(jsonPath(prefix + ".name").value(userDto.getName()))
              .andExpect(jsonPath(prefix + ".email").value(userDto.getEmail()))
              .andExpect(jsonPath(prefix + ".password").value(userDto.getPassword()))
              .andExpect(jsonPath(prefix + ".mobile").value(userDto.getMobile()))
              .andExpect(jsonPath(prefix + ".address").value(userDto.getAddress()))
              .andExpect(jsonPath(prefix + ".birthday").value(userDto.getBirthday().toString()))
              .andExpect(jsonPath(prefix + ".roleId").value(userDto.getRoleId()));
    }

    private void assertUserDtoJson(ResultActions actions, UserDto userDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(userDto.getId()))
              .andExpect(jsonPath("$.name").value(userDto.getName()))
              .andExpect(jsonPath("$.email").value(userDto.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto.getAddress()))
              .andExpect(jsonPath("$.birthday").value(userDto.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(userDto.getRoleId()));
    }
}
