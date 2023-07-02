package com.project.ems.integration.mentor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRestController;
import com.project.ems.mentor.MentorService;
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

import static com.project.ems.constants.EndpointConstants.API_MENTORS;
import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToDtoList;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
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

@WebMvcTest(MentorRestController.class)
@ExtendWith(MockitoExtension.class)
class MentorRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MentorService mentorService;

    @Spy
    private ModelMapper modelMapper;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = convertToDto(modelMapper, getMockedMentor1());
        mentorDto2 = convertToDto(modelMapper, getMockedMentor2());
        mentorDtos = convertToDtoList(modelMapper, getMockedMentors());
    }

    @Test
    void findAll_shouldReturnListOfMentors() throws Exception {
        given(mentorService.findAll()).willReturn(mentorDtos);
        ResultActions actions = mockMvc.perform(get(API_MENTORS)).andExpect(status().isOk());
        for(int i = 0; i < mentorDtos.size(); i++) {
            MentorDto mentorDto = mentorDtos.get(i);
            actions.andExpect(jsonPath("$[" + i + "].id").value(mentorDto.getId()));
            actions.andExpect(jsonPath("$[" + i + "].name").value(mentorDto.getName()));
            actions.andExpect(jsonPath("$[" + i + "].email").value(mentorDto.getEmail()));
            actions.andExpect(jsonPath("$[" + i + "].password").value(mentorDto.getPassword()));
            actions.andExpect(jsonPath("$[" + i + "].mobile").value(mentorDto.getMobile()));
            actions.andExpect(jsonPath("$[" + i + "].address").value(mentorDto.getAddress()));
            actions.andExpect(jsonPath("$[" + i + "].birthday").value(mentorDto.getBirthday().toString()));
            actions.andExpect(jsonPath("$[" + i + "].roleId").value(mentorDto.getRoleId()));
            actions.andExpect(jsonPath("$[" + i + "].employmentType").value(mentorDto.getEmploymentType().toString()));
            actions.andExpect(jsonPath("$[" + i + "].position").value(mentorDto.getPosition().toString()));
            actions.andExpect(jsonPath("$[" + i + "].grade").value(mentorDto.getGrade().toString()));
            actions.andExpect(jsonPath("$[" + i + "].supervisingMentorId").value(mentorDto.getSupervisingMentorId()));
            for(int j = 0; j < mentorDto.getStudiesIds().size(); j++) {
                actions.andExpect(jsonPath("$[" + i + "].studiesIds[" + j + "]").value(mentorDto.getStudiesIds().get(j)));
            }
            for(int j = 0; j < mentorDto.getExperiencesIds().size(); j++) {
                actions.andExpect(jsonPath("$[" + i + "].experiencesIds[" + j + "]").value(mentorDto.getExperiencesIds().get(j)));
            }
            actions.andExpect(jsonPath("$[" + i + "].nrTrainees").value(mentorDto.getNrTrainees()));
            actions.andExpect(jsonPath("$[" + i + "].maxTrainees").value(mentorDto.getMaxTrainees()));
            actions.andExpect(jsonPath("$[" + i + "].isTrainingOpen").value(mentorDto.getIsTrainingOpen()));
        }
        MvcResult result = actions.andReturn();
        List<MentorDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(mentorDtos);
    }

    @Test
    void findById_withValidId_shouldReturnMentorWithGivenId() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        MvcResult result = mockMvc.perform(get(API_MENTORS + "/{id}", VALID_ID))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(mentorDto1.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto1.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(mentorDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(mentorDto1.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(mentorDto1.getEmploymentType().toString()))
              .andExpect(jsonPath("$.position").value(mentorDto1.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(mentorDto1.getGrade().toString()))
              .andExpect(jsonPath("$.supervisingMentorId").value(mentorDto1.getSupervisingMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(mentorDto1.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(mentorDto1.getExperiencesIds().toArray())))
              .andExpect(jsonPath("$.nrTrainees").value(mentorDto1.getNrTrainees()))
              .andExpect(jsonPath("$.maxTrainees").value(mentorDto1.getMaxTrainees()))
              .andExpect(jsonPath("$.isTrainingOpen").value(mentorDto1.getIsTrainingOpen()))
              .andReturn();
        verify(mentorService).findById(VALID_ID);
        MentorDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_MENTORS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddMentorToList() throws Exception {
        given(mentorService.save(any(MentorDto.class))).willReturn(mentorDto1);
        MvcResult result = mockMvc.perform(post(API_MENTORS)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(mentorDto1.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto1.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto1.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto1.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto1.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto1.getAddress()))
              .andExpect(jsonPath("$.birthday").value(mentorDto1.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(mentorDto1.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(mentorDto1.getEmploymentType().toString()))
              .andExpect(jsonPath("$.position").value(mentorDto1.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(mentorDto1.getGrade().toString()))
              .andExpect(jsonPath("$.supervisingMentorId").value(mentorDto1.getSupervisingMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(mentorDto1.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(mentorDto1.getExperiencesIds().toArray())))
              .andExpect(jsonPath("$.nrTrainees").value(mentorDto1.getNrTrainees()))
              .andExpect(jsonPath("$.maxTrainees").value(mentorDto1.getMaxTrainees()))
              .andExpect(jsonPath("$.isTrainingOpen").value(mentorDto1.getIsTrainingOpen()))
              .andReturn();
        verify(mentorService).save(mentorDto1);
        MentorDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateMentorWithGivenId() throws Exception {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willReturn(mentorDto);
        MvcResult result = mockMvc.perform(put(API_MENTORS + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto2)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(mentorDto.getId()))
              .andExpect(jsonPath("$.name").value(mentorDto2.getName()))
              .andExpect(jsonPath("$.email").value(mentorDto2.getEmail()))
              .andExpect(jsonPath("$.password").value(mentorDto2.getPassword()))
              .andExpect(jsonPath("$.mobile").value(mentorDto2.getMobile()))
              .andExpect(jsonPath("$.address").value(mentorDto2.getAddress()))
              .andExpect(jsonPath("$.birthday").value(mentorDto2.getBirthday().toString()))
              .andExpect(jsonPath("$.roleId").value(mentorDto2.getRoleId()))
              .andExpect(jsonPath("$.employmentType").value(mentorDto2.getEmploymentType().toString()))
              .andExpect(jsonPath("$.position").value(mentorDto2.getPosition().toString()))
              .andExpect(jsonPath("$.grade").value(mentorDto2.getGrade().toString()))
              .andExpect(jsonPath("$.supervisingMentorId").value(mentorDto2.getSupervisingMentorId()))
              .andExpect(jsonPath("$.studiesIds").value(containsInAnyOrder(mentorDto2.getStudiesIds().toArray())))
              .andExpect(jsonPath("$.experiencesIds").value(containsInAnyOrder(mentorDto2.getExperiencesIds().toArray())))
              .andExpect(jsonPath("$.nrTrainees").value(mentorDto2.getNrTrainees()))
              .andExpect(jsonPath("$.maxTrainees").value(mentorDto2.getMaxTrainees()))
              .andExpect(jsonPath("$.isTrainingOpen").value(mentorDto2.getIsTrainingOpen()))
              .andReturn();
        verify(mentorService).updateById(mentorDto2, VALID_ID);
        MentorDto response = objectMapper.readValue(result.getResponse().getContentAsString(), MentorDto.class);
        assertThat(response).isEqualTo(mentorDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_MENTORS + "/{id}", INVALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(mentorDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).updateById(mentorDto2, INVALID_ID);
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        mockMvc.perform(delete(API_MENTORS + "/{id}", VALID_ID)).andExpect(status().isNoContent());
        verify(mentorService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(anyInt());
        mockMvc.perform(delete(API_MENTORS + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).deleteById(INVALID_ID);
    }
}
