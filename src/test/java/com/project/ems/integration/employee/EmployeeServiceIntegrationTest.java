package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.employee.EmployeeServiceImpl;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosPage3;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private StudyService studyService;

    @MockBean
    private ExperienceService experienceService;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    private Employee employee1;
    private Employee employee2;
    private List<Employee> employees;
    private Role role1;
    private Role role2;
    private Mentor mentor1;
    private Mentor mentor2;
    private List<Study> studies1;
    private List<Study> studies2;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employees = getMockedEmployees();
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        employeeDto1 = getMockedEmployeeDto1();
        employeeDto2 = getMockedEmployeeDto2();
        employeeDtos = employeeService.convertToDtos(employees);
    }

    @Test
    void findAll_shouldReturnListOfEmployees() {
        given(employeeRepository.findAll()).willReturn(employees);
        List<EmployeeDto> result = employeeService.findAll();
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void findById_withValidId_shouldReturnEmployeeWithGivenId() {
        given(employeeRepository.findById(anyInt())).willReturn(Optional.ofNullable(employee1));
        EmployeeDto result = employeeService.findById(VALID_ID);
        assertThat(result).isEqualTo(employeeDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddEmployeeToList() {
        given(roleService.findEntityById(anyInt())).willReturn(role1);
        given(mentorService.findEntityById(anyInt())).willReturn(mentor1);
        employeeDto1.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies1.get(id - 1)));
        employeeDto1.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences1.get(id - 1)));
        given(employeeRepository.save(any(Employee.class))).willReturn(employee1);
        EmployeeDto result = employeeService.save(employeeDto1);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(result).isEqualTo(employeeService.convertToDto(employeeCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateEmployeeWithGivenId() {
        Employee employee = employee2; employee.setId(VALID_ID);
        given(employeeRepository.findById(anyInt())).willReturn(Optional.ofNullable(employee1));
        given(roleService.findEntityById(anyInt())).willReturn(role2);
        given(mentorService.findEntityById(anyInt())).willReturn(mentor2);
        employeeDto2.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies2.get(id - 3)));
        employeeDto2.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences2.get(id - 3)));
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        EmployeeDto result = employeeService.updateById(employeeDto2, VALID_ID);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(result).isEqualTo(employeeService.convertToDto(employee));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.updateById(employeeDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        given(employeeRepository.findById(anyInt())).willReturn(Optional.ofNullable(employee1));
        employeeService.deleteById(VALID_ID);
        verify(employeeRepository).delete(employee1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> employeeService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EMPLOYEE_NOT_FOUND, INVALID_ID));
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    private Stream<Arguments> paginationArguments() {
        Page<Employee> employeesPage1 = new PageImpl<>(getMockedEmployeesPage1());
        Page<Employee> employeesPage2 = new PageImpl<>(getMockedEmployeesPage2());
        Page<Employee> employeesPage3 = new PageImpl<>(getMockedEmployeesPage3());
        Page<Employee> emptyPage = new PageImpl<>(Collections.emptyList());
        Page<EmployeeDto> employeeDtosPage1 = new PageImpl<>(getMockedEmployeeDtosPage1());
        Page<EmployeeDto> employeeDtosPage2 = new PageImpl<>(getMockedEmployeeDtosPage2());
        Page<EmployeeDto> employeeDtosPage3 = new PageImpl<>(getMockedEmployeeDtosPage3());
        Page<EmployeeDto> emptyDtoPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", EMPLOYEE_FILTER_KEY, employeesPage1, employeeDtosPage1),
                         Arguments.of(1, 2, "id", EMPLOYEE_FILTER_KEY, employeesPage2, employeeDtosPage2),
                         Arguments.of(2, 2, "id", EMPLOYEE_FILTER_KEY, emptyPage, emptyDtoPage),
                         Arguments.of(0, 2, "id", "", employeesPage1, employeeDtosPage1),
                         Arguments.of(1, 2, "id", "", employeesPage2, employeeDtosPage2),
                         Arguments.of(2, 2, "id", "", employeesPage3, employeeDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String key, Page<Employee> entityPage, Page<EmployeeDto> dtoPage) {
        if(key.trim().equals("")) {
            given(employeeRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(employeeRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(entityPage);
        }
        Page<EmployeeDto> result = employeeService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
