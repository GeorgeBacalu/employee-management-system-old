package com.project.ems.experience;

import com.project.ems.wrapper.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.mapper.ExperienceMapper.convertToEntity;
import static com.project.ems.mapper.ExperienceMapper.convertToEntityList;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;

@Controller
@RequiredArgsConstructor
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllExperiencesPage(Model model, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        Page<ExperienceDto> experienceDtosPage = experienceService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrExperiences = experienceDtosPage.getTotalElements();
        int nrPages = experienceDtosPage.getTotalPages();
        model.addAttribute("experiences", convertToEntityList(modelMapper, experienceDtosPage.getContent()));
        model.addAttribute("nrExperiences", nrExperiences);
        model.addAttribute("nrPages", nrPages);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("key", key);
        model.addAttribute("field", field);
        model.addAttribute("direction", direction);
        model.addAttribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size));
        model.addAttribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrExperiences));
        model.addAttribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages));
        model.addAttribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages));
        model.addAttribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction));
        return EXPERIENCES_VIEW;
    }

    @PostMapping("/search")
    public String findAllByKey(@ModelAttribute SearchRequest searchRequest, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", searchRequest.getPage());
        redirectAttributes.addAttribute("size", searchRequest.getSize());
        redirectAttributes.addAttribute("key", searchRequest.getKey());
        redirectAttributes.addAttribute("sort", searchRequest.getSort());
        return REDIRECT_EXPERIENCES_VIEW;
    }

    @GetMapping("/{id}")
    public String getExperienceByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("experience", convertToEntity(modelMapper, experienceService.findById(id)));
        return EXPERIENCE_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveExperiencePage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("experienceDto", id == -1 ? new ExperienceDto() : experienceService.findById(id));
        return SAVE_EXPERIENCE_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute ExperienceDto experienceDto, @PathVariable Integer id) {
        if(id == -1) {
            experienceService.save(experienceDto);
        } else {
            experienceService.updateById(experienceDto, id);
        }
        return REDIRECT_EXPERIENCES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes, @PageableDefault(sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        experienceService.deleteById(id);
        Page<ExperienceDto> experienceDtosPage = experienceService.findAllByKey(pageable, key);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        redirectAttributes.addAttribute("page", !experienceDtosPage.hasContent() && page > 0 ? page - 1 : page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("key", key);
        redirectAttributes.addAttribute("sort", field + "," + direction);
        return REDIRECT_EXPERIENCES_VIEW;
    }
}
