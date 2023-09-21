package com.project.ems.role;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return !roles.isEmpty() ? convertToDtos(roles) : new ArrayList<>();
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
    public List<RoleDto> convertToDtos(List<Role> roles) {
        return roles.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Role> convertToEntities(List<RoleDto> roleDtos) {
        return roleDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public RoleDto convertToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public Role convertToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    @Override
    public Role findEntityById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(RoleDto roleDto, Role role) {
        role.setType(roleDto.getType());
    }
}
