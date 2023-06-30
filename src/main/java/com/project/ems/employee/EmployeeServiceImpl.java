package com.project.ems.employee;

import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mapper.EmployeeMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final MentorService mentorService;
    private final StudyService studyService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> convertToDto(modelMapper, employee)).toList();
    }

    @Override
    public EmployeeDto findById(Integer id) {
        Employee employee = findEntityById(id);
        return convertToDto(modelMapper, employee);
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = convertToEntity(modelMapper, employeeDto, studyService, experienceService);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(modelMapper, savedEmployee);
    }

    @Override
    public EmployeeDto updateById(EmployeeDto employeeDto, Integer id) {
        Employee employeeToUpdate = findEntityById(id);
        updateEntityFromDto(employeeDto, employeeToUpdate);
        Employee updatedEmployee = employeeRepository.save(employeeToUpdate);
        return convertToDto(modelMapper, updatedEmployee);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    private Employee findEntityById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Employee with id %s not found", id)));
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
