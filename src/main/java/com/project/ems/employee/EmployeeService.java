package com.project.ems.employee;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    List<EmployeeDto> findAll();

    EmployeeDto findById(Integer id);

    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto updateById(EmployeeDto employeeDto, Integer id);

    void deleteById(Integer id);

    Page<EmployeeDto> findAllByKey(Pageable pageable, String key);

    List<EmployeeDto> convertToDtos(List<Employee> employees);

    List<Employee> convertToEntities(List<EmployeeDto> employeeDtos);

    EmployeeDto convertToDto(Employee employee);

    Employee convertToEntity(EmployeeDto employeeDto);
}
