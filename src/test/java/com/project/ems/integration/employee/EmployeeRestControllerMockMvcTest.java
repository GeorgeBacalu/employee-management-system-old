package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_EMPLOYEES;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION;
import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(EmployeeRestController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
        given(employeeService.findAll()).willReturn(employeeDtos);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES)).andExpect(status().isOk());
        for(int i = 0; i < employeeDtos.size(); i++) {
            assertEmployeeDto(actions, "$[" + i + "]", employeeDtos.get(i));
        }
        List<EmployeeDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(employeeDtos);
    }

    @Test
    void findById_withValidId_shouldReturnEmployeeWithGivenId() throws Exception {
        given(employeeService.findById(anyInt())).willReturn(employeeDto1);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertEmployeeDtoJson(actions, employeeDto1);
        EmployeeDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto1);
        verify(employeeService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_EMPLOYEES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddEmployeeToList() throws Exception {
        given(employeeService.save(any(EmployeeDto.class))).willReturn(employeeDto1);
        ResultActions actions = mockMvc.perform(post(API_EMPLOYEES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto1)))
              .andExpect(status().isCreated());
        assertEmployeeDtoJson(actions, employeeDto1);
        EmployeeDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto1);
        verify(employeeService).save(employeeDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateEmployeeWithGivenId() throws Exception {
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willReturn(employeeDto);
        ResultActions actions = mockMvc.perform(put(API_EMPLOYEES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto2)))
              .andExpect(status().isOk());
        assertEmployeeDtoJson(actions, employeeDto);
        EmployeeDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto);
        verify(employeeService).updateById(employeeDto2, VALID_ID);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_EMPLOYEES + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).updateById(employeeDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveEmployeeWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_EMPLOYEES + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(employeeService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EMPLOYEE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(employeeService).deleteById(anyInt());
        mockMvc.perform(delete(API_EMPLOYEES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(employeeService).deleteById(INVALID_ID);
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
        given(employeeService.findAllByKey(any(Pageable.class), anyString())).willReturn(expectedPage);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES + API_PAGINATION, page, size, sortField, sortDirection, key)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
        for(int i = 0; i < expectedPage.getContent().size(); i++) {
            assertEmployeeDto(actions, "$.content[" + i + "]", expectedPage.getContent().get(i));
        }
        PageWrapper<EmployeeDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response.getContent()).isEqualTo(expectedPage.getContent());
    }

    private void assertEmployeeDto(ResultActions actions, String prefix, EmployeeDto employeeDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(employeeDto.getId()))
              .andExpect(jsonPath(prefix + ".name").value(employeeDto.getName()))
              .andExpect(jsonPath(prefix + ".email").value(employeeDto.getEmail()))
              .andExpect(jsonPath(prefix + ".password").value(employeeDto.getPassword()))
              .andExpect(jsonPath(prefix + ".mobile").value(employeeDto.getMobile()))
              .andExpect(jsonPath(prefix + ".address").value(employeeDto.getAddress()))
              .andExpect(jsonPath(prefix + ".birthday").value(employeeDto.getBirthday().toString()))
              .andExpect(jsonPath(prefix + ".roleId").value(employeeDto.getRoleId()))
              .andExpect(jsonPath(prefix + ".employmentType").value(employeeDto.getEmploymentType().name()))
              .andExpect(jsonPath(prefix + ".position").value(employeeDto.getPosition().name()))
              .andExpect(jsonPath(prefix + ".grade").value(employeeDto.getGrade().name()))
              .andExpect(jsonPath(prefix + ".mentorId").value(employeeDto.getMentorId()));
        for(int j = 0; j < employeeDto.getStudiesIds().size(); j++) {
            actions.andExpect(jsonPath(prefix + ".studiesIds[" + j + "]").value(employeeDto.getStudiesIds().get(j)));
        }
        for(int j = 0; j < employeeDto.getExperiencesIds().size(); j++) {
            actions.andExpect(jsonPath(prefix + ".experiencesIds[" + j + "]").value(employeeDto.getExperiencesIds().get(j)));
        }
    }

    private void assertEmployeeDtoJson(ResultActions actions, EmployeeDto employeeDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(employeeDto.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(employeeDto.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(employeeDto.getEmploymentType().name()))
              .andExpect(jsonPath("$.position").value(employeeDto.getPosition().name()))
              .andExpect(jsonPath("$.grade").value(employeeDto.getGrade().name()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto.getMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(employeeDto.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(employeeDto.getExperiencesIds().toArray())));
    }
}
