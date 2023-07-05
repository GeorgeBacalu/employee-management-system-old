package com.project.ems.user;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Integer id);

    UserDto save(UserDto userDto);

    UserDto updateById(UserDto userDto, Integer id);

    void deleteById(Integer id);

    Page<UserDto> findAllByKey(Pageable pageable, String key);

    User findEntityById(Integer id);
}
