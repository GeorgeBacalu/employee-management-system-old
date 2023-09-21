package com.project.ems.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.constants.ThymeleafViewConstants.AUTHORITIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.AUTHORITY_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_AUTHORITIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_AUTHORITY_VIEW;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public String getAllAuthoritiesPage(Model model) {
        model.addAttribute("authorities", authorityService.convertToEntities(authorityService.findAll()));
        return AUTHORITIES_VIEW;
    }

    @GetMapping("/{id}")
    public String getAuthorityByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("authority", authorityService.convertToEntity(authorityService.findById(id)));
        return AUTHORITY_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveAuthorityPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("authorityDto", id == -1 ? new AuthorityDto() : authorityService.findById(id));
        return SAVE_AUTHORITY_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute AuthorityDto authorityDto, @PathVariable Integer id) {
        if(id == -1) {
            authorityService.save(authorityDto);
        } else {
            authorityService.updateById(authorityDto, id);
        }
        return REDIRECT_AUTHORITIES_VIEW;
    }
}
