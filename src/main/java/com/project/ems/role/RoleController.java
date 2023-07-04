package com.project.ems.role;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.mapper.RoleMapper.convertToEntity;
import static com.project.ems.mapper.RoleMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllRolesPage(Model model) {
        model.addAttribute("roles", convertToEntityList(modelMapper, roleService.findAll()));
        return "role/roles";
    }

    @GetMapping("/{id}")
    public String getRoleByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("role", convertToEntity(modelMapper, roleService.findById(id)));
        return "role/role-details";
    }

    @GetMapping("/save")
    public String getSaveRolePage(Model model) {
        model.addAttribute("roleDto", new RoleDto());
        return "role/save-role";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute RoleDto roleDto) {
        roleService.save(roleDto);
        return "redirect:/roles";
    }

    @GetMapping("/update/{id}")
    public String getUpdateRolePage(Model model, @PathVariable Integer id) {
        model.addAttribute("roleDto", roleService.findById(id));
        return "role/update-role";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute RoleDto roleDto, @PathVariable Integer id) {
        roleService.updateById(roleDto,id);
        return "redirect:/roles";
    }
}
