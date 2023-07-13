package com.project.ems.user;

import com.project.ems.role.RoleService;
import com.project.ems.wrapper.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_USER_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USERS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.USER_DETAILS_VIEW;
import static com.project.ems.mapper.UserMapper.convertToEntity;
import static com.project.ems.mapper.UserMapper.convertToEntityList;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllUsersPage(Model model, @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<UserDto> userDtosPage = userService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrUsers = userDtosPage.getTotalElements();
        int nrPages = userDtosPage.getTotalPages();
        model.addAttribute("users", convertToEntityList(modelMapper, userDtosPage.getContent(), roleService));
        model.addAttribute("nrUsers", nrUsers);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size));
        model.addAttribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrUsers));
        model.addAttribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages));
        model.addAttribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction));
        return USERS_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        return REDIRECT_USERS_VIEW;
    }

    @GetMapping("/{id}")
    public String getUserByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("user", convertToEntity(modelMapper, userService.findById(id), roleService));
        return USER_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveUserPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("userDto", id == -1 ? new UserDto() : userService.findById(id));
        return SAVE_USER_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute UserDto userDto, @PathVariable Integer id) {
        if(id == -1) {
            userService.save(userDto);
        } else {
            userService.updateById(userDto, id);
        }
        return REDIRECT_USERS_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        userService.deleteById(id);
        Page<UserDto> userDtosPage = userService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", !userDtosPage.hasContent() && page > 0 ? page - 1 : page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("key", key);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        return REDIRECT_USERS_VIEW;
    }
}
