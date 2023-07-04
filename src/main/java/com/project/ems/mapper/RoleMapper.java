package com.project.ems.mapper;

import com.project.ems.role.Role;
import com.project.ems.role.RoleDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleMapper {

    public static RoleDto convertToDto(ModelMapper modelMapper, Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public static Role convertToEntity(ModelMapper modelMapper, RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public static List<RoleDto> convertToDtoList(ModelMapper modelMapper, List<Role> roles) {
        return roles.stream().map(role -> convertToDto(modelMapper, role)).toList();
    }

    public static List<Role> convertToEntityList(ModelMapper modelMapper, List<RoleDto> roleDtos) {
        return roleDtos.stream().map(roleDto -> convertToEntity(modelMapper, roleDto)).toList();
    }
}
