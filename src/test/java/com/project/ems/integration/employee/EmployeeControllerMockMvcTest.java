package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeController;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.EMPLOYEES;
import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDto1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeeDtosFirstPage;
import static com.project.ems.mock.EmployeeMock.getMockedEmployeesFirstPage;
import static com.project.ems.util.PageUtil.getEndIndexCurrentPage;
import static com.project.ems.util.PageUtil.getEndIndexPageNavigation;
import static com.project.ems.util.PageUtil.getSortDirection;
import static com.project.ems.util.PageUtil.getSortField;
import static com.project.ems.util.PageUtil.getStartIndexCurrentPage;
import static com.project.ems.util.PageUtil.getStartIndexPageNavigation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employee;
    private List<Employee> employeesPage1;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> employeeDtosPage1;

    @BeforeEach
    void setUp() {
        employee = getMockedEmployee1();
        employeesPage1 = getMockedEmployeesFirstPage();
        employeeDto = getMockedEmployeeDto1();
        employeeDtosPage1 = getMockedEmployeeDtosFirstPage();
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() throws Exception {
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtosPage1);
        given(employeeService.findAllByKey(any(Pageable.class), anyString())).willReturn(employeeDtosPage);
        given(employeeService.convertToEntities(employeeDtosPage.getContent())).willReturn(employeesPage1);
        int page = pageable.getPageNumber();
        int size = employeeDtosPage1.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        mockMvc.perform(get(EMPLOYEES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEES_VIEW))
              .andExpect(model().attribute("employees", employeesPage1))
              .andExpect(model().attribute("nrEmployees", nrEmployees))
              .andExpect(model().attribute("nrPages", nrPages))
              .andExpect(model().attribute("page", page))
              .andExpect(model().attribute("size", size))
              .andExpect(model().attribute("key", ""))
              .andExpect(model().attribute("field", field))
              .andExpect(model().attribute("direction", direction))
              .andExpect(model().attribute("startIndexCurrentPage", getStartIndexCurrentPage(page, size)))
              .andExpect(model().attribute("endIndexCurrentPage", getEndIndexCurrentPage(page, size, nrEmployees)))
              .andExpect(model().attribute("startIndexPageNavigation", getStartIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("endIndexPageNavigation", getEndIndexPageNavigation(page, nrPages)))
              .andExpect(model().attribute("searchRequest", new SearchRequest(0, size, "", field + "," + direction)));
    }

    @Test
    void findAllByKey_shouldProcessSearchRequestAndReturnListOfEmployeesFilteredByKey() throws Exception {
        mockMvc.perform(post(EMPLOYEES + "/search").accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(employeeDtosPage1.size()))
                    .param("key", EMPLOYEE_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(EMPLOYEES + "?page=*&size=*&key=*&sort=*"));
    }

    @Test
    void getEmployeeByIdPage_withValidId_shouldReturnEmployeeDetailsPage() throws Exception {
        given(employeeService.findById(anyInt())).willReturn(employeeDto);
        given(employeeService.convertToEntity(employeeDto)).willReturn(employee);
        mockMvc.perform(get(EMPLOYEES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEE_DETAILS_VIEW))
              .andExpect(model().attribute("employee", employee));
        verify(employeeService).findById(VALID_ID);
    }

    @Test
    void getEmployeeByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EMPLOYEES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).findById(INVALID_ID);
    }

    @Test
    void getSaveEmployeePage_withNegativeId_shouldReturnSaveEmployeePage() throws Exception {
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("employeeDto", new EmployeeDto()));
    }

    @Test
    void getSaveEmployeePage_withValidId_shouldReturnUpdateEmployeePage() throws Exception {
        given(employeeService.findById(anyInt())).willReturn(employeeDto);
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("employeeDto", employeeDto));
    }

    @Test
    void getSaveEmployeePage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveEmployee() throws Exception {
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(employeeDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl(EMPLOYEES));
        verify(employeeService).save(any(EmployeeDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateEmployeeWithGivenId() throws Exception {
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(employeeDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl(EMPLOYEES));
        verify(employeeService).updateById(employeeDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(employeeDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).updateById(any(EmployeeDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() throws Exception {
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtosPage1);
        given(employeeService.findAllByKey(any(Pageable.class), anyString())).willReturn(employeeDtosPage);
        mockMvc.perform(get(EMPLOYEES + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(employeeDtosPage1.size()))
                    .param("key", EMPLOYEE_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(EMPLOYEES + "?page=*&size=*&key=*&sort=*"));
        verify(employeeService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).deleteById(anyInt());
        mockMvc.perform(get(EMPLOYEES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(EmployeeDto employeeDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", employeeDto.getName());
        params.add("email", employeeDto.getEmail());
        params.add("password", employeeDto.getPassword());
        params.add("mobile", employeeDto.getMobile());
        params.add("address", employeeDto.getAddress());
        params.add("birthday", employeeDto.getBirthday().toString());
        params.add("roleId", employeeDto.getRoleId().toString());
        params.add("employmentType", employeeDto.getEmploymentType().name());
        params.add("position", employeeDto.getPosition().name());
        params.add("grade", employeeDto.getGrade().name());
        params.add("mentorId", employeeDto.getMentorId().toString());
        params.add("studiesIds", employeeDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("experiencesIds", employeeDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        return params;
    }
}
