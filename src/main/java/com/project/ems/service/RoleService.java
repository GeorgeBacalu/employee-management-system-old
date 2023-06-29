package com.project.ems.service;

import com.project.ems.entity.Role;
import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Integer id);

    Role save(Role role);

    Role updateById(Role role, Integer id);

    void deleteById(Integer id);
}
