package com.project.ems.unit.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeController;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesPage1;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;
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

    @Spy
    private Model model;

    @Spy
    private RedirectAttributes redirectAttributes;

    private Employee employee;
    private List<Employee> employees;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employee = getMockedEmployee1();
        employees = getMockedEmployeesPage1();
        employeeDto = getMockedEmployeeDto1();
        employeeDtos = employeeService.convertToDtos(employees);
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() {
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtos);
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        SearchRequest searchRequest = new SearchRequest(0, size, "", field + "," + direction);
        given(employeeService.findAllByKey(pageable, EMPLOYEE_FILTER_KEY)).willReturn(employeeDtosPage);
        given(model.getAttribute("employees")).willReturn(employees);
        given(model.getAttribute("nrEmployees")).willReturn(nrEmployees);
        given(model.getAttribute("nrPages")).willReturn(nrPages);
        given(model.getAttribute("page")).willReturn(page);
        given(model.getAttribute("size")).willReturn(size);
        given(model.getAttribute("key")).willReturn(EMPLOYEE_FILTER_KEY);
        given(model.getAttribute("field")).willReturn(field);
        given(model.getAttribute("direction")).willReturn(direction);
        given(model.getAttribute("startIndexCurrentPage")).willReturn(getStartIndexCurrentPage(page, size));
        given(model.getAttribute("endIndexCurrentPage")).willReturn(getEndIndexCurrentPage(page, size, nrEmployees));
        given(model.getAttribute("startIndexPageNavigation")).willReturn(getStartIndexPageNavigation(page, nrPages));
        given(model.getAttribute("endIndexPageNavigation")).willReturn(getEndIndexPageNavigation(page, nrPages));
        given(model.getAttribute("searchRequest")).willReturn(searchRequest);
        String viewName = employeeController.getAllEmployeesPage(model, pageable, EMPLOYEE_FILTER_KEY);
        assertThat(viewName).isEqualTo(EMPLOYEES_VIEW);
        assertThat(model.getAttribute("employees")).isEqualTo(employees);
        assertThat(model.getAttribute("nrEmployees")).isEqualTo(nrEmployees);
        assertThat(model.getAttribute("nrPages")).isEqualTo(nrPages);
        assertThat(model.getAttribute("page")).isEqualTo(page);
        assertThat(model.getAttribute("size")).isEqualTo(size);
        assertThat(model.getAttribute("key")).isEqualTo(EMPLOYEE_FILTER_KEY);
        assertThat(model.getAttribute("field")).isEqualTo(field);
        assertThat(model.getAttribute("direction")).isEqualTo(direction);
        assertThat(model.getAttribute("startIndexCurrentPage")).isEqualTo(getStartIndexCurrentPage(page, size));
        assertThat(model.getAttribute("endIndexCurrentPage")).isEqualTo(getEndIndexCurrentPage(page, size, nrEmployees));
        assertThat(model.getAttribute("startIndexPageNavigation")).isEqualTo(getStartIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("endIndexPageNavigation")).isEqualTo(getEndIndexPageNavigation(page, nrPages));
        assertThat(model.getAttribute("searchRequest")).isEqualTo(searchRequest);
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfEmployeesFilteredByKey() {
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtos);
        int page = employeeDtosPage.getNumber();
        int size = employeeDtosPage.getSize();
        String sort = getSortField(pageable) + ',' +  getSortDirection(pageable);
        given(redirectAttributes.getAttribute("page")).willReturn(page);
        given(redirectAttributes.getAttribute("size")).willReturn(size);
        given(redirectAttributes.getAttribute("key")).willReturn(EMPLOYEE_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(sort);
        String viewName = employeeController.findAllByKey(new SearchRequest(page, size, EMPLOYEE_FILTER_KEY, sort), redirectAttributes);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(page);
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(size);
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(EMPLOYEE_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(sort);
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
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtos);
        given(employeeService.findAllByKey(pageable, EMPLOYEE_FILTER_KEY)).willReturn(employeeDtosPage);
        given(redirectAttributes.getAttribute("page")).willReturn(employeeDtosPage.getNumber());
        given(redirectAttributes.getAttribute("size")).willReturn(employeeDtosPage.getSize());
        given(redirectAttributes.getAttribute("key")).willReturn(EMPLOYEE_FILTER_KEY);
        given(redirectAttributes.getAttribute("sort")).willReturn(getSortField(pageable) + ',' +  getSortDirection(pageable));
        String viewName = employeeController.deleteById(VALID_ID, redirectAttributes, pageable, EMPLOYEE_FILTER_KEY);
        verify(employeeService).deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EMPLOYEES_VIEW);
        assertThat(redirectAttributes.getAttribute("page")).isEqualTo(employeeDtosPage.getNumber());
        assertThat(redirectAttributes.getAttribute("size")).isEqualTo(employeeDtosPage.getSize());
        assertThat(redirectAttributes.getAttribute("key")).isEqualTo(EMPLOYEE_FILTER_KEY);
        assertThat(redirectAttributes.getAttribute("sort")).isEqualTo(getSortField(pageable) + ',' +  getSortDirection(pageable));
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> employeeController.deleteById(INVALID_ID, redirectAttributes, pageable, EMPLOYEE_FILTER_KEY))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
