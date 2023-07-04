package com.project.ems.mentor;

import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.ems.mapper.MentorMapper.convertToEntity;
import static com.project.ems.mapper.MentorMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mentors")
public class MentorController {

    private final MentorService mentorService;
    private final RoleService roleService;
    private final StudyService studyService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllMentorsPage(Model model) {
        model.addAttribute("mentors", convertToEntityList(modelMapper, mentorService.findAll(), roleService, mentorService, studyService, experienceService));
        return "mentor/mentors";
    }

    @GetMapping("/{id}")
    public String getMentorByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("mentor", convertToEntity(modelMapper, mentorService.findById(id), roleService, mentorService, studyService, experienceService));
        return "mentor/mentor-details";
    }

    @GetMapping("/save")
    public String getSaveMentorPage(Model model) {
        model.addAttribute("mentorDto", new MentorDto());
        return "mentor/save-mentor";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MentorDto mentorDto) {
        mentorService.save(mentorDto);
        return "redirect:/mentors";
    }

    @GetMapping("/update/{id}")
    public String getUpdateMentorPage(Model model, @PathVariable Integer id) {
        model.addAttribute("mentorDto", mentorService.findById(id));
        return "mentor/update-mentor";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute MentorDto mentorDto, @PathVariable Integer id) {
        mentorService.updateById(mentorDto, id);
        return "redirect:/mentors";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        mentorService.deleteById(id);
        return "redirect:/mentors";
    }
}
