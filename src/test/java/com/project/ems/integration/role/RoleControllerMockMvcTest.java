package com.project.ems.integration.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleController;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.ROLES;
import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_ROLES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.ROLES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.ROLE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_ROLE_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRoleDto1;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
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

@WebMvcTest(RoleController.class)
class RoleControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private Role role;
    private List<Role> roles;
    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role = getMockedRole1();
        roles = getMockedRoles();
        roleDto = getMockedRoleDto1();
        roleDtos = roleService.convertToDtos(roles);
    }

    @Test
    void getAllRolesPage_shouldReturnRolesPage() throws Exception {
        given(roleService.findAll()).willReturn(roleDtos);
        given(roleService.convertToEntities(roleDtos)).willReturn(roles);
        mockMvc.perform(get(ROLES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(ROLES_VIEW))
              .andExpect(model().attribute("roles", roles));
        verify(roleService).findAll();
    }

    @Test
    void getRoleByIdPage_withValidId_shouldReturnRoleDetailsPage() throws Exception {
        given(roleService.findById(anyInt())).willReturn(roleDto);
        given(roleService.convertToEntity(roleDto)).willReturn(role);
        mockMvc.perform(get(ROLES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(ROLE_DETAILS_VIEW))
              .andExpect(model().attribute("role", role));
        verify(roleService).findById(VALID_ID);
    }

    @Test
    void getRoleByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(ROLES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(roleService).findById(INVALID_ID);
    }

    @Test
    void getSaveRolePage_withNegativeId_shouldReturnSaveRolePage() throws Exception {
        mockMvc.perform(get(ROLES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_ROLE_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("roleDto", new RoleDto()));
    }

    @Test
    void getSaveRolePage_withValidId_shouldReturnUpdateRolePage() throws Exception {
        given(roleService.findById(anyInt())).willReturn(roleDto);
        mockMvc.perform(get(ROLES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_ROLE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("roleDto", roleDto));
    }

    @Test
    void getSaveRolePage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(ROLES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveRole() throws Exception {
        mockMvc.perform(post(ROLES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(roleDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_ROLES_VIEW))
              .andExpect(redirectedUrl(ROLES));
        verify(roleService).save(any(RoleDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateRoleWithGivenId() throws Exception {
        mockMvc.perform(post(ROLES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(roleDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_ROLES_VIEW))
              .andExpect(redirectedUrl(ROLES));
        verify(roleService).updateById(roleDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.updateById(any(RoleDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(ROLES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(roleDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(roleService).updateById(any(RoleDto.class), anyInt());
    }

    private MultiValueMap<String, String> convertToMultiValueMap(RoleDto roleDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", roleDto.getType().toString());
        return params;
    }
}
