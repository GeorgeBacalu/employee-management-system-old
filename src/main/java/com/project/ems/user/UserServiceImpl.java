package com.project.ems.user;

import com.project.ems.role.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> convertToDto(modelMapper, user)).toList();
    }

    @Override
    public UserDto findById(Integer id) {
        User user = findEntityById(id);
        return convertToDto(modelMapper, user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = convertToEntity(modelMapper, userDto);
        User savedUser = userRepository.save(user);
        return convertToDto(modelMapper, savedUser);
    }

    @Override
    public UserDto updateById(UserDto userDto, Integer id) {
        User userToUpdate = findEntityById(id);
        updateEntityFromDto(userDto, userToUpdate);
        User updatedUser = userRepository.save(userToUpdate);
        return convertToDto(modelMapper, updatedUser);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id %s not found", id)));
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
