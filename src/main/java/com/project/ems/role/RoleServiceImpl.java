package com.project.ems.role;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.ROLE_NOT_FOUND;
import static com.project.ems.mapper.RoleMapper.convertToDto;
import static com.project.ems.mapper.RoleMapper.convertToDtoList;
import static com.project.ems.mapper.RoleMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return convertToDtoList(modelMapper, roles);
    }

    @Override
    public RoleDto findById(Integer id) {
        Role role = findEntityById(id);
        return convertToDto(modelMapper, role);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = convertToEntity(modelMapper, roleDto);
        Role savedRole = roleRepository.save(role);
        return convertToDto(modelMapper, savedRole);
    }

    @Override
    public RoleDto updateById(RoleDto roleDto, Integer id) {
        Role roleToUpdate = findEntityById(id);
        updateEntityFromDto(roleDto, roleToUpdate);
        Role updatedRole = roleRepository.save(roleToUpdate);
        return convertToDto(modelMapper, updatedRole);
    }

    @Override
    public Role findEntityById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ROLE_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(RoleDto roleDto, Role role) {
        role.setAuthority(roleDto.getAuthority());
    }
}
