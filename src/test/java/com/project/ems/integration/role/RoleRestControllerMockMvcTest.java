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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_ROLES;
import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mock.RoleMock.getMockedRoleDto1;
import static com.project.ems.mock.RoleMock.getMockedRoleDto2;
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

    private RoleDto roleDto1;
    private RoleDto roleDto2;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        roleDto1 = getMockedRoleDto1();
        roleDto2 = getMockedRoleDto2();
        roleDtos = roleService.convertToDtos(getMockedRoles());
    }

    @Test
    void findAll_shouldReturnListOfRoles() throws Exception {
        given(roleService.findAll()).willReturn(roleDtos);
        ResultActions actions = mockMvc.perform(get(API_ROLES)).andExpect(status().isOk());
        for(int i = 0; i < roleDtos.size(); i++) {
            assertRoleDto(actions, "$[" + i + "]", roleDtos.get(i));
        }
        List<RoleDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(roleDtos);
    }

    @Test
    void findById_withValidId_shouldReturnRoleWithGivenId() throws Exception {
        given(roleService.findById(anyInt())).willReturn(roleDto1);
        ResultActions actions = mockMvc.perform(get(API_ROLES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertRoleDtoJson(actions, roleDto1);
        RoleDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto1);
        verify(roleService).findById(VALID_ID);
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
        ResultActions actions = mockMvc.perform(post(API_ROLES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto1)))
              .andExpect(status().isCreated());
        assertRoleDtoJson(actions, roleDto1);
        RoleDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto1);
        verify(roleService).save(roleDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateRoleWithGivenId() throws Exception {
        RoleDto roleDto = roleDto2; roleDto.setId(VALID_ID);
        given(roleService.updateById(any(RoleDto.class), anyInt())).willReturn(roleDto);
        ResultActions actions = mockMvc.perform(put(API_ROLES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(roleDto2)))
              .andExpect(status().isOk());
        assertRoleDtoJson(actions, roleDto);
        RoleDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), RoleDto.class);
        assertThat(response).isEqualTo(roleDto);
        verify(roleService).updateById(roleDto2, VALID_ID);
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

    private void assertRoleDto(ResultActions actions, String prefix, RoleDto roleDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(roleDto.getId()));
        actions.andExpect(jsonPath(prefix + ".type").value(roleDto.getType().name()));
    }

    private void assertRoleDtoJson(ResultActions actions, RoleDto roleDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(roleDto.getId()))
              .andExpect(jsonPath("$.type").value(roleDto.getType().name()));
    }
}
