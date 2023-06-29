package com.project.ems.service;

import com.project.ems.entity.User;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    User save(User user);

    User updateById(User user, Integer id);

    void deleteById(Integer id);
}
