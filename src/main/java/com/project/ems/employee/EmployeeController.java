package com.project.ems.employee;

import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
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

import static com.project.ems.mapper.EmployeeMapper.convertToEntity;
import static com.project.ems.mapper.EmployeeMapper.convertToEntityList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final MentorService mentorService;
    private final StudyService studyService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String getAllEmployeesPage(Model model) {
        model.addAttribute("employees", convertToEntityList(modelMapper, employeeService.findAll(), roleService, mentorService, studyService, experienceService));
        return "employee/employees";
    }

    @GetMapping("/{id}")
    public String getEmployeeByIdPage(Model model, @PathVariable Integer id) {
        model.addAttribute("employee", convertToEntity(modelMapper, employeeService.findById(id), roleService, mentorService, studyService, experienceService));
        return "employee/employee-details";
    }

    @GetMapping("/save")
    public String getSaveEmployeePage(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto());
        return "employee/save-employee";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String getUpdateEmployeePage(Model model, @PathVariable Integer id) {
        model.addAttribute("employeeDto", employeeService.findById(id));
        return "employee/update-employee";
    }

    @PostMapping("/update/{id}")
    public String updateById(@ModelAttribute EmployeeDto employeeDto, @PathVariable Integer id) {
        employeeService.updateById(employeeDto, id);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
