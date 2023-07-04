package com.project.ems.experience;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.mapper.ExperienceMapper.convertToEntity;
import static com.project.ems.mapper.ExperienceMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllExperiencesPage(Model model) {
        model.addAttribute("experiences", convertToEntityList(modelMapper, experienceService.findAll()));
        return "experience/experiences";
    }

    @GetMapping("/{id}")
    public String getExperienceByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("experience", convertToEntity(modelMapper, experienceService.findById(id)));
        return "experience/experience-details";
    }

    @GetMapping("/save")
    public String getSaveExperiencePage(Model model) {
        model.addAttribute("experienceDto", new ExperienceDto());
        return "experience/save-experience";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ExperienceDto experienceDto) {
        experienceService.save(experienceDto);
        return "redirect:/experiences";
    }

    @GetMapping("/update/{id}")
    public String getUpdateExperiencePage(Model model, @PathVariable Integer id) {
        model.addAttribute("experienceDto", experienceService.findById(id));
        return "experience/update-experience";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute ExperienceDto experienceDto, @PathVariable Integer id) {
        experienceService.updateById(experienceDto, id);
        return "redirect:/experiences";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return "redirect:/experiences";
    }
}
