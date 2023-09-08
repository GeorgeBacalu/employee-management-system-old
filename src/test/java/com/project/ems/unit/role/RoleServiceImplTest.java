package com.project.ems.unit.role;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.Role;
import com.project.ems.role.RoleDto;
import com.project.ems.role.RoleRepository;
import com.project.ems.role.RoleServiceImpl;
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

import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoleDto1;
import static com.project.ems.mock.RoleMock.getMockedRoleDto2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Role> roleCaptor;

    private Role role1;
    private Role role2;
    private List<Role> roles;
    private RoleDto roleDto1;
    private RoleDto roleDto2;
    private List<RoleDto> roleDtos;

    @BeforeEach
    void setUp() {
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        roles = getMockedRoles();
        roleDto1 = getMockedRoleDto1();
        roleDto2 = getMockedRoleDto2();
        roleDtos = roleService.convertToDtos(roles);
    }

    @Test
    void findAll_shouldReturnListOfRoles() {
        given(roleRepository.findAll()).willReturn(roles);
        List<RoleDto> result = roleService.findAll();
        assertThat(result).isEqualTo(roleDtos);
    }

    @Test
    void findById_withValidId_shouldReturnRoleWithGivenId() {
        given(roleRepository.findById(anyInt())).willReturn(Optional.ofNullable(role1));
        RoleDto result = roleService.findById(VALID_ID);
        assertThat(result).isEqualTo(roleDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> roleService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddRoleToList() {
        given(roleRepository.save(any(Role.class))).willReturn(role1);
        RoleDto result = roleService.save(roleDto1);
        verify(roleRepository).save(roleCaptor.capture());
        assertThat(result).isEqualTo(roleService.convertToDto(roleCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateRoleWithGivenId() {
        Role role = role2; role.setId(VALID_ID);
        given(roleRepository.findById(anyInt())).willReturn(Optional.ofNullable(role1));
        given(roleRepository.save(any(Role.class))).willReturn(role);
        RoleDto result = roleService.updateById(roleDto2, VALID_ID);
        verify(roleRepository).save(roleCaptor.capture());
        assertThat(result).isEqualTo(roleService.convertToDto(roleCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> roleService.updateById(roleDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(ROLE_NOT_FOUND, INVALID_ID));
        verify(roleRepository, never()).save(any(Role.class));
    }
}
