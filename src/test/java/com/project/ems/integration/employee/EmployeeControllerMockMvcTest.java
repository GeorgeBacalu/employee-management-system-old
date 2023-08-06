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
import com.project.ems.wrapper.SearchRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
import static com.project.ems.mock.EmployeeMock.*;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences4;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences5;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences6;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences7;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences8;
import static com.project.ems.mock.MentorMock.*;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static com.project.ems.mock.StudyMock.getMockedStudies3;
import static com.project.ems.mock.StudyMock.getMockedStudies4;
import static com.project.ems.mock.StudyMock.getMockedStudies5;
import static com.project.ems.mock.StudyMock.getMockedStudies6;
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
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;
    private Employee employee6;
    private Employee employee7;
    private Employee employee8;
    private Employee employee9;
    private Employee employee10;
    private Employee employee11;
    private Employee employee12;
    private Employee employee13;
    private Employee employee14;
    private Employee employee15;
    private Employee employee16;
    private Employee employee17;
    private Employee employee18;
    private Employee employee19;
    private Employee employee20;
    private Employee employee21;
    private Employee employee22;
    private Employee employee23;
    private Employee employee24;
    private Employee employee25;
    private Employee employee26;
    private Employee employee27;
    private Employee employee28;
    private Employee employee29;
    private Employee employee30;
    private Employee employee31;
    private Employee employee32;
    private Employee employee33;
    private Employee employee34;
    private Employee employee35;
    private Employee employee36;
    private List<Employee> employees;
    private List<Employee> employeesFirstPage;

    private Role role1;
    private Role role2;
    private Mentor mentor1;
    private Mentor mentor2;
    private Mentor mentor3;
    private Mentor mentor4;
    private Mentor mentor5;
    private Mentor mentor6;
    private Mentor mentor7;
    private Mentor mentor8;
    private Mentor mentor9;
    private Mentor mentor10;
    private Mentor mentor11;
    private Mentor mentor12;
    private Mentor mentor13;
    private Mentor mentor14;
    private Mentor mentor15;
    private Mentor mentor16;
    private Mentor mentor17;
    private Mentor mentor18;
    private Mentor mentor19;
    private Mentor mentor20;
    private Mentor mentor21;
    private Mentor mentor22;
    private Mentor mentor23;
    private Mentor mentor24;
    private Mentor mentor25;
    private Mentor mentor26;
    private Mentor mentor27;
    private Mentor mentor28;
    private Mentor mentor29;
    private Mentor mentor30;
    private Mentor mentor31;
    private Mentor mentor32;
    private Mentor mentor33;
    private Mentor mentor34;
    private Mentor mentor35;
    private Mentor mentor36;
    private List<Study> studies1;
    private List<Study> studies2;
    private List<Study> studies3;
    private List<Study> studies4;
    private List<Study> studies5;
    private List<Study> studies6;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private List<Experience> experiences3;
    private List<Experience> experiences4;
    private List<Experience> experiences5;
    private List<Experience> experiences6;
    private List<Experience> experiences7;
    private List<Experience> experiences8;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private EmployeeDto employeeDto3;
    private EmployeeDto employeeDto4;
    private EmployeeDto employeeDto5;
    private EmployeeDto employeeDto6;
    private EmployeeDto employeeDto7;
    private EmployeeDto employeeDto8;
    private EmployeeDto employeeDto9;
    private EmployeeDto employeeDto10;
    private EmployeeDto employeeDto11;
    private EmployeeDto employeeDto12;
    private EmployeeDto employeeDto13;
    private EmployeeDto employeeDto14;
    private EmployeeDto employeeDto15;
    private EmployeeDto employeeDto16;
    private EmployeeDto employeeDto17;
    private EmployeeDto employeeDto18;
    private EmployeeDto employeeDto19;
    private EmployeeDto employeeDto20;
    private EmployeeDto employeeDto21;
    private EmployeeDto employeeDto22;
    private EmployeeDto employeeDto23;
    private EmployeeDto employeeDto24;
    private EmployeeDto employeeDto25;
    private EmployeeDto employeeDto26;
    private EmployeeDto employeeDto27;
    private EmployeeDto employeeDto28;
    private EmployeeDto employeeDto29;
    private EmployeeDto employeeDto30;
    private EmployeeDto employeeDto31;
    private EmployeeDto employeeDto32;
    private EmployeeDto employeeDto33;
    private EmployeeDto employeeDto34;
    private EmployeeDto employeeDto35;
    private EmployeeDto employeeDto36;
    private List<EmployeeDto> employeeDtos;
    private List<EmployeeDto> employeeDtosFirstPage;

    @BeforeEach
    void setUp() {
        employee1 = getMockedEmployee1();
        employee2 = getMockedEmployee2();
        employee3 = getMockedEmployee3();
        employee4 = getMockedEmployee4();
        employee5 = getMockedEmployee5();
        employee6 = getMockedEmployee6();
        employee7 = getMockedEmployee7();
        employee8 = getMockedEmployee8();
        employee9 = getMockedEmployee9();
        employee10 = getMockedEmployee10();
        employee11 = getMockedEmployee11();
        employee12 = getMockedEmployee12();
        employee13 = getMockedEmployee13();
        employee14 = getMockedEmployee14();
        employee15 = getMockedEmployee15();
        employee16 = getMockedEmployee16();
        employee17 = getMockedEmployee17();
        employee18 = getMockedEmployee18();
        employee19 = getMockedEmployee19();
        employee20 = getMockedEmployee20();
        employee21 = getMockedEmployee21();
        employee22 = getMockedEmployee22();
        employee23 = getMockedEmployee23();
        employee24 = getMockedEmployee24();
        employee25 = getMockedEmployee25();
        employee26 = getMockedEmployee26();
        employee27 = getMockedEmployee27();
        employee28 = getMockedEmployee28();
        employee29 = getMockedEmployee29();
        employee30 = getMockedEmployee30();
        employee31 = getMockedEmployee31();
        employee32 = getMockedEmployee32();
        employee33 = getMockedEmployee33();
        employee34 = getMockedEmployee34();
        employee35 = getMockedEmployee35();
        employee36 = getMockedEmployee36();
        employees = getMockedEmployees();
        employeesFirstPage = List.of(employee1, employee2, employee3, employee4, employee5, employee6, employee7, employee8, employee9, employee10);

        role1 = getMockedRole1();
        role2 = getMockedRole2();
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        mentor3 = getMockedMentor3();
        mentor4 = getMockedMentor4();
        mentor5 = getMockedMentor5();
        mentor6 = getMockedMentor6();
        mentor7 = getMockedMentor7();
        mentor8 = getMockedMentor8();
        mentor9 = getMockedMentor9();
        mentor10 = getMockedMentor10();
        mentor11 = getMockedMentor11();
        mentor12 = getMockedMentor12();
        mentor13 = getMockedMentor13();
        mentor14 = getMockedMentor14();
        mentor15 = getMockedMentor15();
        mentor16 = getMockedMentor16();
        mentor17 = getMockedMentor17();
        mentor18 = getMockedMentor18();
        mentor19 = getMockedMentor19();
        mentor20 = getMockedMentor20();
        mentor21 = getMockedMentor21();
        mentor22 = getMockedMentor22();
        mentor23 = getMockedMentor23();
        mentor24 = getMockedMentor24();
        mentor25 = getMockedMentor25();
        mentor26 = getMockedMentor26();
        mentor27 = getMockedMentor27();
        mentor28 = getMockedMentor28();
        mentor29 = getMockedMentor29();
        mentor30 = getMockedMentor30();
        mentor31 = getMockedMentor31();
        mentor32 = getMockedMentor32();
        mentor33 = getMockedMentor33();
        mentor34 = getMockedMentor34();
        mentor35 = getMockedMentor35();
        mentor36 = getMockedMentor36();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        studies3 = getMockedStudies3();
        studies4 = getMockedStudies4();
        studies5 = getMockedStudies5();
        studies6 = getMockedStudies6();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        experiences3 = getMockedExperiences3();
        experiences4 = getMockedExperiences4();
        experiences5 = getMockedExperiences5();
        experiences6 = getMockedExperiences6();
        experiences7 = getMockedExperiences7();
        experiences8 = getMockedExperiences8();

        employeeDto1 = convertToDto(employee1);
        employeeDto2 = convertToDto(employee2);
        employeeDto3 = convertToDto(employee3);
        employeeDto4 = convertToDto(employee4);
        employeeDto5 = convertToDto(employee5);
        employeeDto6 = convertToDto(employee6);
        employeeDto7 = convertToDto(employee7);
        employeeDto8 = convertToDto(employee8);
        employeeDto9 = convertToDto(employee9);
        employeeDto10 = convertToDto(employee10);
        employeeDto11 = convertToDto(employee11);
        employeeDto12 = convertToDto(employee12);
        employeeDto13 = convertToDto(employee13);
        employeeDto14 = convertToDto(employee14);
        employeeDto15 = convertToDto(employee15);
        employeeDto16 = convertToDto(employee16);
        employeeDto17 = convertToDto(employee17);
        employeeDto18 = convertToDto(employee18);
        employeeDto19 = convertToDto(employee19);
        employeeDto20 = convertToDto(employee20);
        employeeDto21 = convertToDto(employee21);
        employeeDto22 = convertToDto(employee22);
        employeeDto23 = convertToDto(employee23);
        employeeDto24 = convertToDto(employee24);
        employeeDto25 = convertToDto(employee25);
        employeeDto26 = convertToDto(employee26);
        employeeDto27 = convertToDto(employee27);
        employeeDto28 = convertToDto(employee28);
        employeeDto29 = convertToDto(employee29);
        employeeDto30 = convertToDto(employee30);
        employeeDto31 = convertToDto(employee31);
        employeeDto32 = convertToDto(employee32);
        employeeDto33 = convertToDto(employee33);
        employeeDto34 = convertToDto(employee34);
        employeeDto35 = convertToDto(employee35);
        employeeDto36 = convertToDto(employee36);
        employeeDtos = List.of(employeeDto1, employeeDto2, employeeDto3, employeeDto4, employeeDto5, employeeDto6, employeeDto7, employeeDto8, employeeDto9, employeeDto10, employeeDto11, employeeDto12,
                               employeeDto13, employeeDto14, employeeDto15, employeeDto16, employeeDto17, employeeDto18, employeeDto19, employeeDto20, employeeDto21, employeeDto22, employeeDto23, employeeDto24,
                               employeeDto25, employeeDto26, employeeDto27, employeeDto28, employeeDto29, employeeDto30, employeeDto31, employeeDto32, employeeDto33, employeeDto34, employeeDto35, employeeDto36);
        employeeDtosFirstPage = List.of(employeeDto1, employeeDto2, employeeDto3, employeeDto4, employeeDto5, employeeDto6, employeeDto7, employeeDto8, employeeDto9, employeeDto10);

        given(modelMapper.map(employeeDto1, Employee.class)).willReturn(employee1);
        given(modelMapper.map(employeeDto2, Employee.class)).willReturn(employee2);
        given(modelMapper.map(employeeDto3, Employee.class)).willReturn(employee3);
        given(modelMapper.map(employeeDto4, Employee.class)).willReturn(employee4);
        given(modelMapper.map(employeeDto5, Employee.class)).willReturn(employee5);
        given(modelMapper.map(employeeDto6, Employee.class)).willReturn(employee6);
        given(modelMapper.map(employeeDto7, Employee.class)).willReturn(employee7);
        given(modelMapper.map(employeeDto8, Employee.class)).willReturn(employee8);
        given(modelMapper.map(employeeDto9, Employee.class)).willReturn(employee9);
        given(modelMapper.map(employeeDto10, Employee.class)).willReturn(employee10);
        given(modelMapper.map(employeeDto11, Employee.class)).willReturn(employee11);
        given(modelMapper.map(employeeDto12, Employee.class)).willReturn(employee12);
        given(modelMapper.map(employeeDto13, Employee.class)).willReturn(employee13);
        given(modelMapper.map(employeeDto14, Employee.class)).willReturn(employee14);
        given(modelMapper.map(employeeDto15, Employee.class)).willReturn(employee15);
        given(modelMapper.map(employeeDto16, Employee.class)).willReturn(employee16);
        given(modelMapper.map(employeeDto17, Employee.class)).willReturn(employee17);
        given(modelMapper.map(employeeDto18, Employee.class)).willReturn(employee18);
        given(modelMapper.map(employeeDto19, Employee.class)).willReturn(employee19);
        given(modelMapper.map(employeeDto20, Employee.class)).willReturn(employee20);
        given(modelMapper.map(employeeDto21, Employee.class)).willReturn(employee21);
        given(modelMapper.map(employeeDto22, Employee.class)).willReturn(employee22);
        given(modelMapper.map(employeeDto23, Employee.class)).willReturn(employee23);
        given(modelMapper.map(employeeDto24, Employee.class)).willReturn(employee24);
        given(modelMapper.map(employeeDto25, Employee.class)).willReturn(employee25);
        given(modelMapper.map(employeeDto26, Employee.class)).willReturn(employee26);
        given(modelMapper.map(employeeDto27, Employee.class)).willReturn(employee27);
        given(modelMapper.map(employeeDto28, Employee.class)).willReturn(employee28);
        given(modelMapper.map(employeeDto29, Employee.class)).willReturn(employee29);
        given(modelMapper.map(employeeDto30, Employee.class)).willReturn(employee30);
        given(modelMapper.map(employeeDto31, Employee.class)).willReturn(employee31);
        given(modelMapper.map(employeeDto32, Employee.class)).willReturn(employee32);
        given(modelMapper.map(employeeDto33, Employee.class)).willReturn(employee33);
        given(modelMapper.map(employeeDto34, Employee.class)).willReturn(employee34);
        given(modelMapper.map(employeeDto35, Employee.class)).willReturn(employee35);
        given(modelMapper.map(employeeDto36, Employee.class)).willReturn(employee36);

        given(roleService.findEntityById(employeeDto1.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto2.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto3.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto4.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto5.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto6.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto7.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto8.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto9.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto10.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto11.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto12.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto13.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto14.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto15.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto16.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto17.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto18.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto19.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto20.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto21.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto22.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto23.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto24.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto25.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto26.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto27.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto28.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto29.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto30.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto31.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto32.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto33.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto34.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto35.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(employeeDto36.getRoleId())).willReturn(role1);

        given(mentorService.findEntityById(employeeDto1.getMentorId())).willReturn(mentor1);
        given(mentorService.findEntityById(employeeDto2.getMentorId())).willReturn(mentor2);
        given(mentorService.findEntityById(employeeDto3.getMentorId())).willReturn(mentor3);
        given(mentorService.findEntityById(employeeDto4.getMentorId())).willReturn(mentor4);
        given(mentorService.findEntityById(employeeDto5.getMentorId())).willReturn(mentor5);
        given(mentorService.findEntityById(employeeDto6.getMentorId())).willReturn(mentor6);
        given(mentorService.findEntityById(employeeDto7.getMentorId())).willReturn(mentor7);
        given(mentorService.findEntityById(employeeDto8.getMentorId())).willReturn(mentor8);
        given(mentorService.findEntityById(employeeDto9.getMentorId())).willReturn(mentor9);
        given(mentorService.findEntityById(employeeDto10.getMentorId())).willReturn(mentor10);
        given(mentorService.findEntityById(employeeDto11.getMentorId())).willReturn(mentor11);
        given(mentorService.findEntityById(employeeDto12.getMentorId())).willReturn(mentor12);
        given(mentorService.findEntityById(employeeDto13.getMentorId())).willReturn(mentor13);
        given(mentorService.findEntityById(employeeDto14.getMentorId())).willReturn(mentor14);
        given(mentorService.findEntityById(employeeDto15.getMentorId())).willReturn(mentor15);
        given(mentorService.findEntityById(employeeDto16.getMentorId())).willReturn(mentor16);
        given(mentorService.findEntityById(employeeDto17.getMentorId())).willReturn(mentor17);
        given(mentorService.findEntityById(employeeDto18.getMentorId())).willReturn(mentor18);
        given(mentorService.findEntityById(employeeDto19.getMentorId())).willReturn(mentor19);
        given(mentorService.findEntityById(employeeDto20.getMentorId())).willReturn(mentor20);
        given(mentorService.findEntityById(employeeDto21.getMentorId())).willReturn(mentor21);
        given(mentorService.findEntityById(employeeDto22.getMentorId())).willReturn(mentor22);
        given(mentorService.findEntityById(employeeDto23.getMentorId())).willReturn(mentor23);
        given(mentorService.findEntityById(employeeDto24.getMentorId())).willReturn(mentor24);
        given(mentorService.findEntityById(employeeDto25.getMentorId())).willReturn(mentor25);
        given(mentorService.findEntityById(employeeDto26.getMentorId())).willReturn(mentor26);
        given(mentorService.findEntityById(employeeDto27.getMentorId())).willReturn(mentor27);
        given(mentorService.findEntityById(employeeDto28.getMentorId())).willReturn(mentor28);
        given(mentorService.findEntityById(employeeDto29.getMentorId())).willReturn(mentor29);
        given(mentorService.findEntityById(employeeDto30.getMentorId())).willReturn(mentor30);
        given(mentorService.findEntityById(employeeDto31.getMentorId())).willReturn(mentor31);
        given(mentorService.findEntityById(employeeDto32.getMentorId())).willReturn(mentor32);
        given(mentorService.findEntityById(employeeDto33.getMentorId())).willReturn(mentor33);
        given(mentorService.findEntityById(employeeDto34.getMentorId())).willReturn(mentor34);
        given(mentorService.findEntityById(employeeDto35.getMentorId())).willReturn(mentor35);
        given(mentorService.findEntityById(employeeDto36.getMentorId())).willReturn(mentor36);

        given(studyService.findEntityById(employeeDto1.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto1.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto2.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto2.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(employeeDto3.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(employeeDto3.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(employeeDto4.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(employeeDto4.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(employeeDto5.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(employeeDto5.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(employeeDto6.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(employeeDto6.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(employeeDto7.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto7.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto8.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto8.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(employeeDto9.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(employeeDto9.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(employeeDto10.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(employeeDto10.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(employeeDto11.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(employeeDto11.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(employeeDto12.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(employeeDto12.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(employeeDto13.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto13.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto14.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto14.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(employeeDto15.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(employeeDto15.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(employeeDto16.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(employeeDto16.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(employeeDto17.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(employeeDto17.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(employeeDto18.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(employeeDto18.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(employeeDto19.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto19.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto20.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto20.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(employeeDto21.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(employeeDto21.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(employeeDto22.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(employeeDto22.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(employeeDto23.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(employeeDto23.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(employeeDto24.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(employeeDto24.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(employeeDto25.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto25.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto26.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto26.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(employeeDto27.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(employeeDto27.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(employeeDto28.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(employeeDto28.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(employeeDto29.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(employeeDto29.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(employeeDto30.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(employeeDto30.getStudiesIds().get(1))).willReturn(studies6.get(1));
        given(studyService.findEntityById(employeeDto31.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(employeeDto31.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(employeeDto32.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(employeeDto32.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(studyService.findEntityById(employeeDto33.getStudiesIds().get(0))).willReturn(studies3.get(0));
        given(studyService.findEntityById(employeeDto33.getStudiesIds().get(1))).willReturn(studies3.get(1));
        given(studyService.findEntityById(employeeDto34.getStudiesIds().get(0))).willReturn(studies4.get(0));
        given(studyService.findEntityById(employeeDto34.getStudiesIds().get(1))).willReturn(studies4.get(1));
        given(studyService.findEntityById(employeeDto35.getStudiesIds().get(0))).willReturn(studies5.get(0));
        given(studyService.findEntityById(employeeDto35.getStudiesIds().get(1))).willReturn(studies5.get(1));
        given(studyService.findEntityById(employeeDto36.getStudiesIds().get(0))).willReturn(studies6.get(0));
        given(studyService.findEntityById(employeeDto36.getStudiesIds().get(1))).willReturn(studies6.get(1));

        given(experienceService.findEntityById(employeeDto1.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(employeeDto1.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(employeeDto2.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(employeeDto2.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(employeeDto3.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(employeeDto3.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(employeeDto4.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(employeeDto4.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(employeeDto5.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(employeeDto5.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(employeeDto6.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(employeeDto6.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(employeeDto7.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(employeeDto7.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(employeeDto8.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(employeeDto8.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(employeeDto9.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(employeeDto9.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(employeeDto10.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(employeeDto10.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(employeeDto11.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(employeeDto11.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(employeeDto12.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(employeeDto12.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(employeeDto13.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(employeeDto13.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(employeeDto14.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(employeeDto14.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(employeeDto15.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(employeeDto15.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(employeeDto16.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(employeeDto16.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(employeeDto17.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(employeeDto17.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(employeeDto18.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(employeeDto18.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(employeeDto19.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(employeeDto19.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(employeeDto20.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(employeeDto20.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(employeeDto21.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(employeeDto21.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(employeeDto22.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(employeeDto22.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(employeeDto23.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(employeeDto23.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(employeeDto24.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(employeeDto24.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(employeeDto25.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(employeeDto25.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(employeeDto26.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(employeeDto26.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(employeeDto27.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(employeeDto27.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(employeeDto28.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(employeeDto28.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
        given(experienceService.findEntityById(employeeDto29.getExperiencesIds().get(0))).willReturn(experiences5.get(0));
        given(experienceService.findEntityById(employeeDto29.getExperiencesIds().get(1))).willReturn(experiences5.get(1));
        given(experienceService.findEntityById(employeeDto30.getExperiencesIds().get(0))).willReturn(experiences6.get(0));
        given(experienceService.findEntityById(employeeDto30.getExperiencesIds().get(1))).willReturn(experiences6.get(1));
        given(experienceService.findEntityById(employeeDto31.getExperiencesIds().get(0))).willReturn(experiences7.get(0));
        given(experienceService.findEntityById(employeeDto31.getExperiencesIds().get(1))).willReturn(experiences7.get(1));
        given(experienceService.findEntityById(employeeDto32.getExperiencesIds().get(0))).willReturn(experiences8.get(0));
        given(experienceService.findEntityById(employeeDto32.getExperiencesIds().get(1))).willReturn(experiences8.get(1));
        given(experienceService.findEntityById(employeeDto33.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(employeeDto33.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(employeeDto34.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(employeeDto34.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
        given(experienceService.findEntityById(employeeDto35.getExperiencesIds().get(0))).willReturn(experiences3.get(0));
        given(experienceService.findEntityById(employeeDto35.getExperiencesIds().get(1))).willReturn(experiences3.get(1));
        given(experienceService.findEntityById(employeeDto36.getExperiencesIds().get(0))).willReturn(experiences4.get(0));
        given(experienceService.findEntityById(employeeDto36.getExperiencesIds().get(1))).willReturn(experiences4.get(1));
    }

    @Test
    void getAllEmployeesPage_shouldReturnEmployeesPage() throws Exception {
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtosFirstPage);
        given(employeeService.findAllByKey(any(Pageable.class), anyString())).willReturn(employeeDtosPage);
        int page = pageable.getPageNumber();
        int size = employeeDtosFirstPage.size();
        String field = getSortField(pageable);
        String direction = getSortDirection(pageable);
        long nrEmployees = employeeDtosPage.getTotalElements();
        int nrPages = employeeDtosPage.getTotalPages();
        mockMvc.perform(get(EMPLOYEES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EMPLOYEES_VIEW))
              .andExpect(model().attribute("employees", employeesFirstPage))
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
                    .param("size", String.valueOf(employeeDtosFirstPage.size()))
                    .param("key", EMPLOYEE_FILTER_KEY)
                    .param("sort", getSortField(pageable) + "," + getSortDirection(pageable)))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrlPattern(EMPLOYEES + "?page=*&size=*&key=*&sort=*"));
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
        PageImpl<EmployeeDto> employeeDtosPage = new PageImpl<>(employeeDtosFirstPage);
        given(employeeService.findAllByKey(any(Pageable.class), anyString())).willReturn(employeeDtosPage);
        mockMvc.perform(get(EMPLOYEES + "/delete/{id}", VALID_ID).accept(TEXT_HTML)
                    .param("page", String.valueOf(pageable.getPageNumber()))
                    .param("size", String.valueOf(employeeDtosFirstPage.size()))
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
