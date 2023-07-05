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
}
