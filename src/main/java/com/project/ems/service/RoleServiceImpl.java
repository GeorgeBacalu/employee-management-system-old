package com.project.ems.service;

import com.project.ems.entity.Role;
import com.project.ems.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Role with id %s not found", id)));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateById(Role role, Integer id) {
        Role roleToUpdate = findById(id);
        roleToUpdate.setAuthority(role.getAuthority());
        return roleRepository.save(roleToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
