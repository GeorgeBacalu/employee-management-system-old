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

import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_ROLES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.ROLES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.ROLE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_ROLE_VIEW;
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
        return ROLES_VIEW;
    }

    @GetMapping("/{id}")
    public String getRoleByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("role", convertToEntity(modelMapper, roleService.findById(id)));
        return ROLE_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveRolePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("roleDto", id == -1 ? new RoleDto() : roleService.findById(id));
        return SAVE_ROLE_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute RoleDto roleDto, @PathVariable Integer id) {
        if(id == -1) {
            roleService.save(roleDto);
        } else {
            roleService.updateById(roleDto, id);
        }
        return REDIRECT_ROLES_VIEW;
    }
}
