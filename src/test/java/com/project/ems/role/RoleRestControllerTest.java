package com.project.ems.role;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.RoleMapper.convertToDto;
import static com.project.ems.mapper.RoleMapper.convertToDtoList;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.RoleMock.getMockedRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleRestControllerTest {

    @InjectMocks
    private RoleRestController roleRestController;

    @Mock
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
    void findAll_shouldReturnListOfRoles() {
        given(roleService.findAll()).willReturn(roleDtos);
        ResponseEntity<List<RoleDto>> response = roleRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDtos);
    }

    @Test
    void findById_shouldReturnRoleWithGivenId() {
        given(roleService.findById(anyInt())).willReturn(roleDto1);
        ResponseEntity<RoleDto> response = roleRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDto1);
    }

    @Test
    void save_shouldAddRoleToList() {
        given(roleService.save(any(RoleDto.class))).willReturn(roleDto1);
        ResponseEntity<RoleDto> response = roleRestController.save(roleDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(roleDto1);
    }

    @Test
    void updateById_shouldUpdateRoleWithGivenId() {
        RoleDto roleDto = roleDto2; roleDto.setId(VALID_ID);
        given(roleService.updateById(any(RoleDto.class), anyInt())).willReturn(roleDto);
        ResponseEntity<RoleDto> response = roleRestController.updateById(roleDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDto);
    }

    @Test
    void deleteById_shouldRemoveRoleWithGivenIdFromList() {
        ResponseEntity<Void> response = roleRestController.deleteById(VALID_ID);
        verify(roleService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
