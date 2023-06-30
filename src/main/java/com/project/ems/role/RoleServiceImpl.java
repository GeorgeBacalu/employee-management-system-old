package com.project.ems.role;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::convertToDto).toList();
    }

    @Override
    public RoleDto findById(Integer id) {
        Role role = findEntityById(id);
        return convertToDto(role);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = convertToEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        return convertToDto(savedRole);
    }

    @Override
    public RoleDto updateById(RoleDto roleDto, Integer id) {
        Role roleToUpdate = findEntityById(id);
        updateEntityFromDto(roleDto, roleToUpdate);
        Role updatedRole = roleRepository.save(roleToUpdate);
        return convertToDto(updatedRole);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findEntityById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Role with id %s not found", id)));
    }

    private RoleDto convertToDto(Role role) {
        return RoleDto.builder()
              .id(role.getId())
              .authority(role.getAuthority())
              .build();
    }

    private Role convertToEntity(RoleDto roleDto) {
        return Role.builder()
              .id(roleDto.getId())
              .authority(roleDto.getAuthority())
              .build();
    }

    private void updateEntityFromDto(RoleDto roleDto, Role role) {
        role.setAuthority(roleDto.getAuthority());
    }
}
