package com.project.ems.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id %s not found", id)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateById(User user, Integer id) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setMobile(user.getMobile());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setBirthday(user.getBirthday());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
