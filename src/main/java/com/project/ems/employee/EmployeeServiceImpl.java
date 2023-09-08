package com.project.ems.employee;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;

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
        return !employees.isEmpty() ? convertToDtos(employees) : new ArrayList<>();
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
        Employee employeeToDelete = findEntityById(id);
        employeeRepository.delete(employeeToDelete);
    }

    @Override
    public Page<EmployeeDto> findAllByKey(Pageable pageable, String key) {
        Page<Employee> employeesPage = key.trim().equals("") ? employeeRepository.findAll(pageable) : employeeRepository.findAllByKey(pageable, key.toLowerCase());
        return employeesPage.hasContent() ? employeesPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public List<EmployeeDto> convertToDtos(List<Employee> employees) {
        return employees.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Employee> convertToEntities(List<EmployeeDto> employeeDtos) {
        return employeeDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setStudiesIds(employee.getStudies().stream().map(Study::getId).toList());
        employeeDto.setExperiencesIds(employee.getExperiences().stream().map(Experience::getId).toList());
        return employeeDto;
    }

    @Override
    public Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setRole(roleService.findEntityById(employeeDto.getRoleId()));
        employee.setMentor(mentorService.findEntityById(employeeDto.getMentorId()));
        employee.setStudies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        return employee;
    }

    private Employee findEntityById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
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
        employee.setStudies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).collect(Collectors.toList()));
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).collect(Collectors.toList()));
    }
}
