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

import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_STUDY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.STUDY_DETAILS_VIEW;
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
        return STUDIES_VIEW;
    }

    @GetMapping("/{id}")
    public String getStudyByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("study", convertToEntity(modelMapper, studyService.findById(id)));
        return STUDY_DETAILS_VIEW;
    }

    @GetMapping("/save/{id}")
    public String getSaveStudyPage(Model model, @PathVariable Integer id) {
        model.addAttribute("id", id);
        model.addAttribute("studyDto", id == -1 ? new StudyDto() : studyService.findById(id));
        return SAVE_STUDY_VIEW;
    }

    @PostMapping("/save/{id}")
    public String save(@ModelAttribute StudyDto studyDto, @PathVariable Integer id) {
        if(id == -1) {
            studyService.save(studyDto);
        } else {
            studyService.updateById(studyDto, id);
        }
        return REDIRECT_STUDIES_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return REDIRECT_STUDIES_VIEW;
    }
}
