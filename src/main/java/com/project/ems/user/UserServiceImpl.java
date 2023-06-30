package com.project.ems.user;

import com.project.ems.role.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).toList();
    }

    @Override
    public UserDto findById(Integer id) {
        User user = findEntityById(id);
        return convertToDto(user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Override
    public UserDto updateById(UserDto userDto, Integer id) {
        User userToUpdate = findEntityById(id);
        updateEntityFromDto(userDto, userToUpdate);
        User updatedUser = userRepository.save(userToUpdate);
        return convertToDto(updatedUser);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id %s not found", id)));
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
              .id(user.getId())
              .name(user.getName())
              .email(user.getEmail())
              .password(user.getPassword())
              .mobile(user.getMobile())
              .address(user.getAddress())
              .birthday(user.getBirthday())
              .roleId(user.getRole().getId())
              .build();
    }

    private User convertToEntity(UserDto userDto) {
        return User.builder()
              .id(userDto.getId())
              .name(userDto.getName())
              .email(userDto.getEmail())
              .password(userDto.getPassword())
              .mobile(userDto.getMobile())
              .address(userDto.getAddress())
              .birthday(userDto.getBirthday())
              .role(roleService.findEntityById(userDto.getRoleId()))
              .build();
    }

    private void updateEntityFromDto(UserDto userDto, User user) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setMobile(userDto.getMobile());
        user.setAddress(userDto.getAddress());
        user.setBirthday(userDto.getBirthday());
        user.setRole(roleService.findEntityById(userDto.getRoleId()));
    }
}
