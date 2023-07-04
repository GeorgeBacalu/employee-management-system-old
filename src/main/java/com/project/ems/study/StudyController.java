package com.project.ems.study;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.mapper.StudyMapper.convertToEntity;
import static com.project.ems.mapper.StudyMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studies")
public class StudyController {

    private final StudyService studyService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllStudiesPage(Model model) {
        model.addAttribute("studies", convertToEntityList(modelMapper, studyService.findAll()));
        return "study/studies";
    }

    @GetMapping("/{id}")
    public String getStudyByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("study", convertToEntity(modelMapper, studyService.findById(id)));
        return "study/study-details";
    }

    @GetMapping("/save")
    public String getSaveStudyPage(Model model) {
        model.addAttribute("studyDto", new StudyDto());
        return "study/save-study";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute StudyDto studyDto) {
        studyService.save(studyDto);
        return "redirect:/studies";
    }

    @GetMapping("/update/{id}")
    public String getUpdateStudyPage(Model model, @PathVariable Integer id) {
        model.addAttribute("studyDto", studyService.findById(id));
        return "study/update-study";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute StudyDto studyDto, @PathVariable Integer id) {
        studyService.updateById(studyDto, id);
        return "redirect:/studies";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return "redirect:/studies";
    }
}
