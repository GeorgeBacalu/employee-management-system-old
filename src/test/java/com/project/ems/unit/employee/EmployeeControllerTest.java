package com.project.ems.unit.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeController;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mapper.EmployeeMapper.convertToDtoList;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private RoleService roleService;

    @Mock
    private MentorService mentorService;

    @Mock
    private StudyService studyService;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Employee employee;
    private List<Employee> employees;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee = getMockedEmployee1();
        employees = getMockedEmployees();
        employeeDto = convertToDto(modelMapper, employee);
        employeeDtos = convertToDtoList(modelMapper, employees);
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() {
        given(employeeService.findAll()).willReturn(employeeDtos);
        given(model.getAttribute(anyString())).willReturn(employees);
        String viewName = employeeController.getAllEmployeesPage(model);
        assertThat(viewName).isEqualTo(EMPLOYEES_VIEW);
        assertThat(model.getAttribute("employees")).isEqualTo(employees);
    }

    @Test
    void getEmployeeByIdPage_withValidId_shouldReturnEmployeeDetailsPage() {
        given(employeeService.findById(anyInt())).willReturn(employeeDto);
        given(model.getAttribute(anyString())).willReturn(employee);
        String viewName = employeeController.getEmployeeByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(EMPLOYEE_DETAILS_VIEW);
        assertThat(model.getAttribute("employee")).isEqualTo(employee);
    }

    @Test
    void getEmployeeByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> employeeController.getEmployeeByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveEmployeePage_withNegativeId_shouldReturnSaveEmployeePage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("employeeDto")).willReturn(new EmployeeDto());
        String viewName = employeeController.getSaveEmployeePage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_EMPLOYEE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("employeeDto")).isEqualTo(new EmployeeDto());
    }

    @Test
    void getSaveEmployeePage_withValidId_shouldReturnUpdateEmployeePage() {
        given(employeeService.findById(anyInt())).willReturn(employeeDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("employeeDto")).willReturn(employeeDto);
        String viewName = employeeController.getSaveEmployeePage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_EMPLOYEE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("employeeDto")).isEqualTo(employeeDto);
    }

    @Test
    void getSaveEmployeePage_withInvalidId_shouldThrowException() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> employeeController.getSaveEmployeePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveEmployee() {
        String viewName = employeeController.save(employeeDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        verify(employeeService).save(employeeDto);
    }

    @Test
    void save_withValidId_shouldUpdateEmployeeWithGivenId() {
        String viewName = employeeController.save(employeeDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        verify(employeeService).updateById(employeeDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(employeeDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> employeeController.save(employeeDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() {
        String viewName = employeeController.deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        verify(employeeService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> employeeController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
