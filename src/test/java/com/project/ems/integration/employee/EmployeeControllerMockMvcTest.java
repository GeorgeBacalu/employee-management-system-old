package com.project.ems.integration.employee;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeController;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.Role;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.EMPLOYEES;
import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EMPLOYEE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EMPLOYEES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EMPLOYEE_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private StudyService studyService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private ModelMapper modelMapper;

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
        employeeDto1 = convertToDto(employee1);
        employeeDto2 = convertToDto(employee2);
        employeeDtos = List.of(employeeDto1, employeeDto2);

        given(modelMapper.map(employeeDto1, Employee.class)).willReturn(employee1);
        given(modelMapper.map(employeeDto2, Employee.class)).willReturn(employee2);
        given(roleService.findEntityById(employeeDto1.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto2.getRoleId())).willReturn(role2);
        given(mentorService.findEntityById(employeeDto1.getMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(employeeDto2.getMentorId())).willReturn(mentor2);
        given(studyService.findEntityById(employeeDto1.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto1.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto2.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto2.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(experienceService.findEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() throws Exception {
        given(employeeService.findAll()).willReturn(employeeDtos);
        mockMvc.perform(get(EMPLOYEES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEES_VIEW))
              .andExpect(model().attribute("employees", employees));
        verify(employeeService).findAll();
    }

    @Test
    void getEmployeeByIdPage_withValidId_shouldReturnEmployeeDetailsPage() throws Exception {
        given(employeeService.findById(anyInt())).willReturn(employeeDto1);
        mockMvc.perform(get(EMPLOYEES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEE_DETAILS_VIEW))
              .andExpect(model().attribute("employee", employee1));
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
        given(employeeService.findById(anyInt())).willReturn(employeeDto1);
        mockMvc.perform(get(EMPLOYEES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EMPLOYEE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("employeeDto", employeeDto1));
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
                    .params(convertToMultiValueMap(employeeDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl(EMPLOYEES));
        verify(employeeService).save(any(EmployeeDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateEmployeeWithGivenId() throws Exception {
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(employeeDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl(EMPLOYEES));
        verify(employeeService).updateById(employeeDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(EMPLOYEES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(employeeDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).updateById(any(EmployeeDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() throws Exception {
        mockMvc.perform(get(EMPLOYEES + "/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EMPLOYEES_VIEW))
              .andExpect(redirectedUrl(EMPLOYEES));
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
        params.add("employmentType", employeeDto.getEmploymentType().toString());
        params.add("position", employeeDto.getPosition().toString());
        params.add("grade", employeeDto.getGrade().toString());
        params.add("mentorId", employeeDto.getMentorId().toString());
        params.add("studiesIds", employeeDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("experiencesIds", employeeDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        return params;
    }

    private EmployeeDto convertToDto(Employee employee) {
        return EmployeeDto.builder()
              .id(employee.getId())
              .name(employee.getName())
              .email(employee.getEmail())
              .password(employee.getPassword())
              .mobile(employee.getMobile())
              .address(employee.getAddress())
              .birthday(employee.getBirthday())
              .roleId(employee.getRole().getId())
              .employmentType(employee.getEmploymentType())
              .position(employee.getPosition())
              .grade(employee.getGrade())
              .mentorId(employee.getMentor().getId())
              .studiesIds(employee.getStudies().stream().map(Study::getId).toList())
              .experiencesIds(employee.getExperiences().stream().map(Experience::getId).toList())
              .build();
    }
}
