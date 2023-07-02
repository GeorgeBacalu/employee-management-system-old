package com.project.ems.mapper;

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

    public static User convertToEntity(ModelMapper modelMapper, UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public static List<UserDto> convertToDtoLiSt(ModelMapper modelMapper, List<User> users) {
        return users.stream().map(user -> convertToDto(modelMapper, user)).toList();
    }
}
