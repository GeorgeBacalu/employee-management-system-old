package com.project.ems.mapper;

import com.project.ems.role.RoleService;
import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto convertToDto(ModelMapper modelMapper, User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public static User convertToEntity(ModelMapper modelMapper, UserDto userDto, RoleService roleService) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(roleService.findEntityById(userDto.getRoleId()));
        return user;
    }

    public static List<UserDto> convertToDtoLiSt(ModelMapper modelMapper, List<User> users) {
        return users.stream().map(user -> convertToDto(modelMapper, user)).toList();
    }

    public static List<User> convertToEntityList(ModelMapper modelMapper, List<UserDto> userDtos, RoleService roleService) {
        return userDtos.stream().map(userDto -> convertToEntity(modelMapper, userDto, roleService)).toList();
    }
}
