package com.project.ems.role;

import java.util.List;

public interface RoleService {

    List<RoleDto> findAll();

    RoleDto findById(Integer id);

    RoleDto save(RoleDto roleDto);

    RoleDto updateById(RoleDto roleDto, Integer id);

    List<RoleDto> convertToDtos(List<Role> roles);

    List<Role> convertToEntities(List<RoleDto> roleDtos);

    RoleDto convertToDto(Role role);

    Role convertToEntity(RoleDto roleDto);

    Role findEntityById(Integer id);
}
