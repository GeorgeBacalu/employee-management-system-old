package com.project.ems.user;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.feedback.FeedbackRepository;
import com.project.ems.role.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToDtoLiSt;
import static com.project.ems.mapper.UserMapper.convertToEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return convertToDtoLiSt(modelMapper, users);
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
        User userToDelete = findEntityById(id);
        feedbackRepository.deleteAllByUser(userToDelete);
        userRepository.delete(userToDelete);
    }

    @Override
    public User findEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
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
