package com.project.ems.employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> findAll();

    EmployeeDto findById(Integer id);

    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto updateById(EmployeeDto employeeDto, Integer id);

    void deleteById(Integer id);
}
