package com.project.ems.user;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthorityService authorityService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return !users.isEmpty() ? convertToDtos(users) : new ArrayList<>();
    }

    @Override
    public UserDto findById(Integer id) {
        User user = findEntityById(id);
        return convertToDto(user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        User userToDelete = findEntityById(id);
        userRepository.delete(userToDelete);
    }

    @Override
    public Page<UserDto> findAllByKey(Pageable pageable, String key) {
        Page<User> usersPage = key.trim().equals("") ? userRepository.findAll(pageable) : userRepository.findAllByKey(pageable, key.toLowerCase());
        return usersPage.hasContent() ? usersPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public List<UserDto> convertToDtos(List<User> users) {
        return users.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<User> convertToEntities(List<UserDto> userDtos) {
        return userDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setAuthoritiesIds(user.getAuthorities().stream().map(Authority::getId).toList());
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(roleService.findEntityById(userDto.getRoleId()));
        user.setAuthorities(userDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).toList());
        return user;
    }

    @Override
    public User findEntityById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(UserDto userDto, User user) {
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setMobile(userDto.getMobile());
        user.setAddress(userDto.getAddress());
        user.setBirthday(userDto.getBirthday());
        user.setRole(roleService.findEntityById(userDto.getRoleId()));
        user.setAuthorities(userDto.getAuthoritiesIds().stream().map(authorityService::findEntityById).collect(Collectors.toList()));
    }
}
