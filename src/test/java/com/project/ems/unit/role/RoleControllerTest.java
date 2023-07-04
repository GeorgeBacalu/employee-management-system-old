package com.project.ems.unit.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleController;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_ROLES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.ROLES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.ROLE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_ROLE_VIEW;
import static com.project.ems.mapper.RoleMapper.convertToDto;
import static com.project.ems.mapper.RoleMapper.convertToDtoList;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Role role;
    private List<Role> roles;
    private RoleDto roleDto;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role = getMockedRole1();
        roles = getMockedRoles();
        roleDto = convertToDto(modelMapper, role);
        roleDtos = convertToDtoList(modelMapper, roles);
    }

    @Test
    void getAllRolesPage_shouldReturnRolesPage() {
        given(roleService.findAll()).willReturn(roleDtos);
        given(model.getAttribute(anyString())).willReturn(roles);
        String viewName = roleController.getAllRolesPage(model);
        assertThat(viewName).isEqualTo(ROLES_VIEW);
        assertThat(model.getAttribute("roles")).isEqualTo(roles);
    }

    @Test
    void getRoleByIdPage_withValidId_shouldReturnRoleDetailsPage() {
        given(roleService.findById(anyInt())).willReturn(roleDto);
        given(model.getAttribute(anyString())).willReturn(role);
        String viewName = roleController.getRoleByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(ROLE_DETAILS_VIEW);
        assertThat(model.getAttribute("role")).isEqualTo(role);
    }

    @Test
    void getRoleByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> roleController.getRoleByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveRolePage_withNegativeId_shouldReturnSaveRolePage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("roleDto")).willReturn(new RoleDto());
        String viewName = roleController.getSaveRolePage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_ROLE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("roleDto")).isEqualTo(new RoleDto());
    }

    @Test
    void getSaveRolePage_withValidId_shouldReturnUpdateRolePage() {
        given(roleService.findById(anyInt())).willReturn(roleDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("roleDto")).willReturn(roleDto);
        String viewName = roleController.getSaveRolePage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_ROLE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("roleDto")).isEqualTo(roleDto);
    }

    @Test
    void getSaveRolePage_withInvalidId_shouldThrowException() {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> roleController.getSaveRolePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveRole() {
        String viewName = roleController.save(roleDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_ROLES_VIEW);
        verify(roleService).save(roleDto);
    }

    @Test
    void save_withValidId_shouldUpdateRoleWithGivenId() {
        String viewName = roleController.save(roleDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_ROLES_VIEW);
        verify(roleService).updateById(roleDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(ROLE_NOT_FOUND, INVALID_ID);
        given(roleService.updateById(roleDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> roleController.save(roleDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
