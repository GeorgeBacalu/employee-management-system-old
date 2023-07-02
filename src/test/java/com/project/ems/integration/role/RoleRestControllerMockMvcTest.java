package com.project.ems.integration.role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRestController;
import com.project.ems.role.RoleService;
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

import static com.project.ems.constants.EndpointConstants.API_ROLES;
import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.RoleMapper.convertToDto;
import static com.project.ems.mapper.RoleMapper.convertToDtoList;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleRestController.class)
@ExtendWith(MockitoExtension.class)
class RoleRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @Spy
    private ModelMapper modelMapper;

    private RoleDto roleDto1;
    private RoleDto roleDto2;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        roleDto1 = convertToDto(modelMapper, getMockedRole1());
        roleDto2 = convertToDto(modelMapper, getMockedRole2());
        roleDtos = convertToDtoList(modelMapper, getMockedRoles());
    }

    @Test
    void findAll_shouldReturnListOfRoles() throws Exception {
        given(roleService.findAll()).willReturn(roleDtos);
        ResultActions actions = mockMvc.perform(get(API_ROLES)).andExpect(status().isOk());
        for(int i = 0; i < roleDtos.size(); i++) {
            RoleDto roleDto = roleDtos.get(i);
            actions.andExpect(jsonPath("$[" + i + "].id").value(roleDto.getId()));
            actions.andExpect(jsonPath("$[" + i + "].authority").value(roleDto.getAuthority().toString()));
        }
        MvcResult result = actions.andReturn();
        List<RoleDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(roleDtos);
    }

    @Test
    void findById_withValidId_shouldReturnRoleWithGivenId() throws Exception {
        given(roleService.findById(anyInt())).willReturn(roleDto1);
        MvcResult result = mockMvc.perform(get(API_ROLES + "/{id}", VALID_ID))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(roleDto1.getId()))
              .andExpect(jsonPath("$.authority").value(roleDto1.getAuthority().toString()))
              .andReturn();
        verify(roleService).findById(VALID_ID);
        RoleDto response = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_ROLES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof  ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(roleService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddRoleToList() throws Exception {
        given(roleService.save(any(RoleDto.class))).willReturn(roleDto1);
        MvcResult result = mockMvc.perform(post(API_ROLES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(roleDto1.getId()))
              .andExpect(jsonPath("$.authority").value(roleDto1.getAuthority().toString()))
              .andReturn();
        verify(roleService).save(roleDto1);
        RoleDto response = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateRoleWithGivenId() throws Exception {
        RoleDto roleDto = roleDto2; roleDto.setId(VALID_ID);
        given(roleService.updateById(any(RoleDto.class), anyInt())).willReturn(roleDto);
        MvcResult result = mockMvc.perform(put(API_ROLES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(roleDto.getId()))
              .andExpect(jsonPath("$.authority").value(roleDto2.getAuthority().toString()))
              .andReturn();
        verify(roleService).updateById(roleDto2, VALID_ID);
        RoleDto response = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.updateById(any(RoleDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_ROLES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof  ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(roleService).updateById(roleDto2, INVALID_ID);
    }
}
