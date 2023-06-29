package com.project.ems.user;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    User save(User user);

    User updateById(User user, Integer id);

    void deleteById(Integer id);
}
