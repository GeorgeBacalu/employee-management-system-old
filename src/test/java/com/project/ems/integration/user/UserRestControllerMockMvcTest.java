package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserDto;
import com.project.ems.user.UserRestController;
import com.project.ems.user.UserService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_USERS;
import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToDtoList;
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
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@ExtendWith(MockitoExtension.class)
class UserRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        userDto1 = convertToDto(modelMapper, getMockedUser1());
        userDto2 = convertToDto(modelMapper, getMockedUser2());
        userDtos = convertToDtoList(modelMapper, getMockedUsers());
    }

    @Test
    void findAll_shouldReturnListOfUsers() throws Exception {
        given(userService.findAll()).willReturn(userDtos);
        ResultActions actions = mockMvc.perform(get(API_USERS)).andExpect(status().isOk());
        for(int i = 0; i < userDtos.size(); i++) {
            UserDto userDto = userDtos.get(i);
            actions.andExpect(jsonPath("$[" + i + "].id").value(userDto.getId()));
            actions.andExpect(jsonPath("$[" + i + "].name").value(userDto.getName()));
            actions.andExpect(jsonPath("$[" + i + "].email").value(userDto.getEmail()));
            actions.andExpect(jsonPath("$[" + i + "].password").value(userDto.getPassword()));
            actions.andExpect(jsonPath("$[" + i + "].mobile").value(userDto.getMobile()));
            actions.andExpect(jsonPath("$[" + i + "].address").value(userDto.getAddress()));
            actions.andExpect(jsonPath("$[" + i + "].birthday").value(userDto.getBirthday().toString()));
            actions.andExpect(jsonPath("$[" + i + "].roleId").value(userDto.getRoleId()));
        }
        MvcResult result = actions.andReturn();
        List<UserDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(userDtos);
    }

    @Test
    void findById_withValidId_shouldReturnUserWithGivenId() throws Exception {
        given(userService.findById(anyInt())).willReturn(userDto1);
        MvcResult result = mockMvc.perform(get(API_USERS + "/{id}", VALID_ID))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(userDto1.getId()))
              .andExpect(jsonPath("$.name").value(userDto1.getName()))
              .andExpect(jsonPath("$.email").value(userDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(userDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(userDto1.getRoleId()))
              .andReturn();
        verify(userService).findById(VALID_ID);
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto1);
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
        MvcResult result = mockMvc.perform(post(API_USERS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(userDto1.getId()))
              .andExpect(jsonPath("$.name").value(userDto1.getName()))
              .andExpect(jsonPath("$.email").value(userDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(userDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(userDto1.getRoleId()))
              .andReturn();
        verify(userService).save(userDto1);
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateUserWithGivenId() throws Exception {
        UserDto userDto = userDto2; userDto.setId(VALID_ID);
        given(userService.updateById(any(UserDto.class), anyInt())).willReturn(userDto);
        MvcResult result = mockMvc.perform(put(API_USERS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(userDto2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(userDto.getId()))
              .andExpect(jsonPath("$.name").value(userDto2.getName()))
              .andExpect(jsonPath("$.email").value(userDto2.getEmail()))
              .andExpect(jsonPath("$.password").value(userDto2.getPassword()))
              .andExpect(jsonPath("$.mobile").value(userDto2.getMobile()))
              .andExpect(jsonPath("$.address").value(userDto2.getAddress()))
              .andExpect(jsonPath("$.birthday").value(userDto2.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(userDto2.getRoleId()))
              .andReturn();
        verify(userService).updateById(userDto2, VALID_ID);
        UserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(response).isEqualTo(userDto);
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
}
