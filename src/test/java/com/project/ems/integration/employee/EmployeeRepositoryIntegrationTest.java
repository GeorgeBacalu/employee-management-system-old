package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> filteredEmployeesPage1;

    @BeforeEach
    void setUp() {
        filteredEmployeesPage1 = getMockedEmployeesPage1();
    }

    @Test
    void findAllByKey_shouldReturnListOfEmployeesFilteredByKeyPage1() {
        assertThat(employeeRepository.findAllByKey(pageable, EMPLOYEE_FILTER_KEY).getContent()).isEqualTo(filteredEmployeesPage1);
    }
}
