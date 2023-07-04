package com.project.ems.user;

import com.project.ems.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.mapper.UserMapper.convertToEntity;
import static com.project.ems.mapper.UserMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllUsersPage(Model model) {
        model.addAttribute("users", convertToEntityList(modelMapper, userService.findAll(), roleService));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String getUserByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("user", convertToEntity(modelMapper, userService.findById(id), roleService));
        return "user/user-details";
    }

    @GetMapping("/save")
    public String getSaveUserPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/save-user";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute UserDto userDto) {
        userService.save(userDto);
        return "redirect:/users";
    }

    @GetMapping("/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Integer id) {
        model.addAttribute("userDto", userService.findById(id));
        return "user/update-user";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute UserDto userDto, @PathVariable Integer id) {
        userService.updateById(userDto, id);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
