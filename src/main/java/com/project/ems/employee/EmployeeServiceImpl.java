package com.project.ems.employee;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final MentorService mentorService;
    private final StudyService studyService;
    private final ExperienceService experienceService;

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::convertToDto).toList();
    }

    @Override
    public EmployeeDto findById(Integer id) {
        Employee employee = findEntityById(id);
        return convertToDto(employee);
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateById(EmployeeDto employeeDto, Integer id) {
        Employee employeeToUpdate = findEntityById(id);
        updateEntityFromDto(employeeDto, employeeToUpdate);
        Employee updatedEmployee = employeeRepository.save(employeeToUpdate);
        return convertToDto(updatedEmployee);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    private Employee findEntityById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Employee with id %s not found", id)));
    }

    private EmployeeDto convertToDto(Employee employee) {
        return EmployeeDto.builder()
              .id(employee.getId())
              .name(employee.getName())
              .email(employee.getEmail())
              .password(employee.getPassword())
              .mobile(employee.getMobile())
              .address(employee.getAddress())
              .birthday(employee.getBirthday())
              .roleId(employee.getRole().getId())
              .employmentType(employee.getEmploymentType())
              .position(employee.getPosition())
              .grade(employee.getGrade())
              .mentorId(employee.getMentor().getId())
              .studiesIds(employee.getStudies().stream().map(Study::getId).toList())
              .experiencesIds(employee.getExperiences().stream().map(Experience::getId).toList())
              .build();
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        return Employee.builder()
              .id(employeeDto.getId())
              .name(employeeDto.getName())
              .email(employeeDto.getEmail())
              .password(employeeDto.getPassword())
              .mobile(employeeDto.getMobile())
              .address(employeeDto.getAddress())
              .birthday(employeeDto.getBirthday())
              .role(roleService.findEntityById(employeeDto.getRoleId()))
              .employmentType(employeeDto.getEmploymentType())
              .position(employeeDto.getPosition())
              .grade(employeeDto.getGrade())
              .mentor(mentorService.findEntityById(employeeDto.getMentorId()))
              .studies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).toList())
              .experiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList())
              .build();
    }

    private void updateEntityFromDto(EmployeeDto employeeDto, Employee employee) {
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setMobile(employeeDto.getMobile());
        employee.setAddress(employeeDto.getAddress());
        employee.setBirthday(employeeDto.getBirthday());
        employee.setRole(roleService.findEntityById(employeeDto.getRoleId()));
        employee.setEmploymentType(employeeDto.getEmploymentType());
        employee.setPosition(employeeDto.getPosition());
        employee.setGrade(employeeDto.getGrade());
        employee.setMentor(mentorService.findEntityById(employeeDto.getMentorId()));
        employee.setStudies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
    }
}
