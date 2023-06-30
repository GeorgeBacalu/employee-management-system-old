package com.project.ems.role;

import java.util.List;

public interface RoleService {

    List<RoleDto> findAll();

    RoleDto findById(Integer id);

    RoleDto save(RoleDto roleDto);

    RoleDto updateById(RoleDto roleDto, Integer id);

    void deleteById(Integer id);

    Role findEntityById(Integer id);
}
