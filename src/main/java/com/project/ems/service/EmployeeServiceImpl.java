package com.project.ems.service;

import com.project.ems.entity.Employee;
import com.project.ems.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Employee with id %s not found", id)));
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateById(Employee employee, Integer id) {
        Employee employeeToUpdate = findById(id);
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setEmail(employee.getEmail());
        employeeToUpdate.setPassword(employee.getPassword());
        employeeToUpdate.setMobile(employee.getMobile());
        employeeToUpdate.setAddress(employee.getAddress());
        employeeToUpdate.setBirthday(employee.getBirthday());
        employeeToUpdate.setRole(employee.getRole());
        employeeToUpdate.setEmploymentType(employee.getEmploymentType());
        employeeToUpdate.setPosition(employee.getPosition());
        employeeToUpdate.setGrade(employee.getGrade());
        employeeToUpdate.setMentor(employee.getMentor());
        employeeToUpdate.setStudies(employee.getStudies());
        employeeToUpdate.setExperiences(employee.getExperiences());
        return employeeRepository.save(employeeToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }
}
