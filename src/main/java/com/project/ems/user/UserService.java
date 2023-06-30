package com.project.ems.user;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Integer id);

    UserDto save(UserDto userDto);

    UserDto updateById(UserDto userDto, Integer id);

    void deleteById(Integer id);

    User findEntityById(Integer id);
}
