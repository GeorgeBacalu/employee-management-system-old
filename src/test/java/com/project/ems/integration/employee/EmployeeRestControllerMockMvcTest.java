package com.project.ems.integration.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.EmployeeRestController;
import com.project.ems.employee.EmployeeService;
import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_EMPLOYEES;
import static com.project.ems.constants.ExceptionMessageConstants.EMPLOYEE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.EmployeeMapper.convertToDto;
import static com.project.ems.mapper.EmployeeMapper.convertToDtoList;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee2;
import static com.project.ems.mock.EmployeeMock.getMockedEmployees;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

@WebMvcTest(EmployeeRestController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Spy
    private ModelMapper modelMapper;

    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private List<EmployeeDto> employeeDtos;

    @BeforeEach
    void setUp() {
        employeeDto1 = convertToDto(modelMapper, getMockedEmployee1());
        employeeDto2 = convertToDto(modelMapper, getMockedEmployee2());
        employeeDtos = convertToDtoList(modelMapper, getMockedEmployees());
    }

    @Test
    void findAll_shouldReturnListOfEmployees() throws Exception {
        given(employeeService.findAll()).willReturn(employeeDtos);
        ResultActions actions = mockMvc.perform(get(API_EMPLOYEES)).andExpect(status().isOk());
        for(int i = 0; i < employeeDtos.size(); i++) {
            EmployeeDto employeeDto = employeeDtos.get(i);
            actions.andExpect(jsonPath("$[" + i + "].id").value(employeeDto.getId()));
            actions.andExpect(jsonPath("$[" + i + "].name").value(employeeDto.getName()));
            actions.andExpect(jsonPath("$[" + i + "].email").value(employeeDto.getEmail()));
            actions.andExpect(jsonPath("$[" + i + "].password").value(employeeDto.getPassword()));
            actions.andExpect(jsonPath("$[" + i + "].mobile").value(employeeDto.getMobile()));
            actions.andExpect(jsonPath("$[" + i + "].address").value(employeeDto.getAddress()));
            actions.andExpect(jsonPath("$[" + i + "].birthday").value(employeeDto.getBirthday().toString()));
            actions.andExpect(jsonPath("$[" + i + "].roleId").value(employeeDto.getRoleId()));
            actions.andExpect(jsonPath("$[" + i + "].employmentType").value(employeeDto.getEmploymentType().toString()));
            actions.andExpect(jsonPath("$[" + i + "].position").value(employeeDto.getPosition().toString()));
            actions.andExpect(jsonPath("$[" + i + "].grade").value(employeeDto.getGrade().toString()));
            actions.andExpect(jsonPath("$[" + i + "].mentorId").value(employeeDto.getMentorId()));
            for(int j = 0; j < employeeDto.getStudiesIds().size(); j++) {
                actions.andExpect(jsonPath("$[" + i + "].studiesIds[" + j + "]").value(employeeDto.getStudiesIds().get(j)));
            }
            for(int j = 0; j < employeeDto.getExperiencesIds().size(); j++) {
                actions.andExpect(jsonPath("$[" + i + "].experiencesIds[" + j + "]").value(employeeDto.getExperiencesIds().get(j)));
            }
        }
        MvcResult result = actions.andReturn();
        List<EmployeeDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(employeeDtos);
    }

    @Test
    void findById_withValidId_shouldReturnEmployeeWithGivenId() throws Exception {
        given(employeeService.findById(anyInt())).willReturn(employeeDto1);
        MvcResult result = mockMvc.perform(get(API_EMPLOYEES + "/{id}", VALID_ID))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(employeeDto1.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto1.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(employeeDto1.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(employeeDto1.getEmploymentType().toString()))
              .andExpect(jsonPath("$.position").value(employeeDto1.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(employeeDto1.getGrade().toString()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto1.getMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(employeeDto1.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(employeeDto1.getExperiencesIds().toArray())))
              .andReturn();
        verify(employeeService).findById(VALID_ID);
        EmployeeDto response = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto1);
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
        MvcResult result = mockMvc.perform(post(API_EMPLOYEES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(employeeDto1.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto1.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(employeeDto1.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(employeeDto1.getEmploymentType().toString()))
              .andExpect(jsonPath("$.position").value(employeeDto1.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(employeeDto1.getGrade().toString()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto1.getMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(employeeDto1.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(employeeDto1.getExperiencesIds().toArray())))
              .andReturn();
        verify(employeeService).save(employeeDto1);
        EmployeeDto response = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateEmployeeWithGivenId() throws Exception {
        EmployeeDto employeeDto = employeeDto2; employeeDto.setId(VALID_ID);
        given(employeeService.updateById(any(EmployeeDto.class), anyInt())).willReturn(employeeDto);
        MvcResult result = mockMvc.perform(put(API_EMPLOYEES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(employeeDto2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(employeeDto.getId()))
              .andExpect(jsonPath("$.name").value(employeeDto2.getName()))
              .andExpect(jsonPath("$.email").value(employeeDto2.getEmail()))
              .andExpect(jsonPath("$.password").value(employeeDto2.getPassword()))
              .andExpect(jsonPath("$.mobile").value(employeeDto2.getMobile()))
              .andExpect(jsonPath("$.address").value(employeeDto2.getAddress()))
              .andExpect(jsonPath("$.birthday").value(employeeDto2.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(employeeDto2.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(employeeDto2.getEmploymentType().toString()))
              .andExpect(jsonPath("$.position").value(employeeDto2.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(employeeDto2.getGrade().toString()))
              .andExpect(jsonPath("$.mentorId").value(employeeDto2.getMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(employeeDto2.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(employeeDto2.getExperiencesIds().toArray())))
              .andReturn();
        verify(employeeService).updateById(employeeDto2, VALID_ID);
        EmployeeDto response = objectMapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);
        assertThat(response).isEqualTo(employeeDto);
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
}
