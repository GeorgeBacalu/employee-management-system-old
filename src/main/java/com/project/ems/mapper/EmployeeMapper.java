package com.project.ems.mapper;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMapper {

    public static EmployeeDto convertToDto(ModelMapper modelMapper, Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        employeeDto.setStudiesIds(employee.getStudies().stream().map(Study::getId).toList());
        employeeDto.setExperiencesIds(employee.getExperiences().stream().map(Experience::getId).toList());
        return employeeDto;
    }

    public static Employee convertToEntity(ModelMapper modelMapper, EmployeeDto employeeDto, StudyService studyService, ExperienceService experienceService) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setStudies(employeeDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        employee.setExperiences(employeeDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        return employee;
    }
}
