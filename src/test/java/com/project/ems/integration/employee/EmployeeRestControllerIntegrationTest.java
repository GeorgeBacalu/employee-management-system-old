package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.wrapper.PageWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.EndpointConstants.API_EMPLOYEES;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION_V2;
import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage3;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmployeeRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
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
    void findAll_shouldReturnListOfEmployees() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void findById_withValidId_shouldReturnEmployeeWithGivenId() {
        ResponseEntity<EmployeeDto> response = template.getForEntity(API_EMPLOYEES + "/" + VALID_ID, EmployeeDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(employeeDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddEmployeeToList() throws Exception {
        ResponseEntity<EmployeeDto> saveResponse = template.postForEntity(API_EMPLOYEES, employeeDto1, EmployeeDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveResponse.getBody()).isEqualTo(employeeDto1);

        ResponseEntity<String> getAllResponse = template.getForEntity(API_EMPLOYEES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void updateById_withValidId_shouldUpdateEmployeeWithGivenId() {
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        ResponseEntity<EmployeeDto> updateResponse = template.exchange(API_EMPLOYEES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(employeeDto2), EmployeeDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo(employeeDto);

        ResponseEntity<EmployeeDto> getResponse = template.getForEntity(API_EMPLOYEES + "/" + VALID_ID, EmployeeDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_EMPLOYEES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(employeeDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() throws Exception {
        ResponseEntity<EmployeeDto> deleteResponse = template.exchange(API_EMPLOYEES + "/" + VALID_ID, HttpMethod.DELETE, null, EmployeeDto.class);
        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = template.getForEntity(API_EMPLOYEES + "/" + VALID_ID, String.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, VALID_ID)));

        ResponseEntity<String> getAllResponse = template.getForEntity(API_EMPLOYEES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<EmployeeDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        List<EmployeeDto> employeeDtosCopy = new ArrayList<>(employeeDtos);
        employeeDtosCopy.remove(employeeDto1);
        assertThat(result).isEqualTo(employeeDtosCopy);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_EMPLOYEES + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EMPLOYEE_NOT_FOUND, INVALID_ID)));
    }

    private Stream<Arguments> paginationArguments() {
        Page<EmployeeDto> employeeDtosPage1 = new PageImpl<>(getMockedEmployeeDtosPage1());
        Page<EmployeeDto> employeeDtosPage2 = new PageImpl<>(getMockedEmployeeDtosPage2());
        Page<EmployeeDto> employeeDtosPage3 = new PageImpl<>(getMockedEmployeeDtosPage3());
        Page<EmployeeDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", EMPLOYEE_FILTER_KEY, employeeDtosPage1),
                         Arguments.of(1, 2, "id", "asc", EMPLOYEE_FILTER_KEY, employeeDtosPage2),
                         Arguments.of(2, 2, "id", "asc", EMPLOYEE_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", employeeDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", employeeDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", employeeDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<EmployeeDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EMPLOYEES + String.format(API_PAGINATION_V2, page, size, sortField, sortDirection, key), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<EmployeeDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
