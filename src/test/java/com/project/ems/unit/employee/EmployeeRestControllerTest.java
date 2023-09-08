package com.project.ems.unit.employee;

import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage3;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Mock
    private EmployeeService employeeService;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = employeeService.convertToDtos(getMockedEmployees());
    }

    @Test
    void findAll_shouldReturnListOfEmployees() {
        given(employeeService.findAll()).willReturn(employeeDtos);
        ResponseEntity<List<EmployeeDto>> response = employeeRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDtos);
    }

    @Test
    void findById_shouldReturnEmployeeWithGivenId() {
        given(employeeService.findById(anyInt())).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void save_shouldAddEmployeeToList() {
        given(employeeService.save(any(EmployeeDto.class))).willReturn(employeeDto1);
        ResponseEntity<EmployeeDto> response = employeeRestController.save(employeeDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void updateById_shouldUpdateEmployeeWithGivenId() {
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willReturn(employeeDto);
        ResponseEntity<EmployeeDto> response = employeeRestController.updateById(employeeDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void deleteById_shouldRemoveEmployeeWithGivenIdFromList() {
        ResponseEntity<Void> response = employeeRestController.deleteById(VALID_ID);
        verify(employeeService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @ParameterizedTest
    @CsvSource({ "1, ${EMPLOYEE_FILTER_KEY}", "2, ${EMPLOYEE_FILTER_KEY}", "3, ${EMPLOYEE_FILTER_KEY}", "1, ''", "2, ''", "3, ''" })
    void findAllByKey_shouldReturnListOfEmployeesFilteredByKey(int page, String key) {
        Pair<List<EmployeeDto>, Pageable> pair = switch(page) {
            case 1 -> Pair.of(getMockedEmployeeDtosPage1(), pageable);
            case 2 -> Pair.of(getMockedEmployeeDtosPage2(), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : getMockedEmployeeDtosPage3(), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
        Page<EmployeeDto> filteredEmployeeDtosPage = new PageImpl<>(pair.getLeft());
        given(employeeService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredEmployeeDtosPage);
        ResponseEntity<PageWrapper<EmployeeDto>> response = employeeRestController.findAllByKey(pair.getRight(), key);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredEmployeeDtosPage.getContent()));
    }
}
