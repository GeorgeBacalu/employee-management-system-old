package com.project.ems.integration.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
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

import static com.project.ems.constants.EndpointConstants.MENTORS;
import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_MENTOR_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
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

@WebMvcTest(MentorController.class)
class MentorControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private StudyService studyService;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private ModelMapper modelMapper;

    private Mentor mentor1;
    private Mentor mentor2;
    private List<Mentor> mentors;
    private Role role1;
    private Role role2;
    private List<Study> studies1;
    private List<Study> studies2;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        mentors = getMockedMentors();
        role1 = getMockedRole1();
        role2 = getMockedRole2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        mentorDto1 = convertToDto(mentor1);
        mentorDto2 = convertToDto(mentor2);
        mentorDtos = List.of(mentorDto1, mentorDto2);

        given(modelMapper.map(mentorDto1, Mentor.class)).willReturn(mentor1);
        given(modelMapper.map(mentorDto2, Mentor.class)).willReturn(mentor2);
        given(roleService.findEntityById(mentorDto1.getRoleId())).willReturn(role1);
        given(roleService.findEntityById(mentorDto2.getRoleId())).willReturn(role2);
        given(mentorService.findEntityById(mentorDto1.getSupervisingMentorId())).willReturn(null);
        given(mentorService.findEntityById(mentorDto2.getSupervisingMentorId())).willReturn(mentor1);
        given(studyService.findEntityById(mentorDto1.getStudiesIds().get(0))).willReturn(studies1.get(0));
        given(studyService.findEntityById(mentorDto1.getStudiesIds().get(1))).willReturn(studies1.get(1));
        given(studyService.findEntityById(mentorDto2.getStudiesIds().get(0))).willReturn(studies2.get(0));
        given(studyService.findEntityById(mentorDto2.getStudiesIds().get(1))).willReturn(studies2.get(1));
        given(experienceService.findEntityById(mentorDto1.getExperiencesIds().get(0))).willReturn(experiences1.get(0));
        given(experienceService.findEntityById(mentorDto1.getExperiencesIds().get(1))).willReturn(experiences1.get(1));
        given(experienceService.findEntityById(mentorDto2.getExperiencesIds().get(0))).willReturn(experiences2.get(0));
        given(experienceService.findEntityById(mentorDto2.getExperiencesIds().get(1))).willReturn(experiences2.get(1));
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() throws Exception {
        given(mentorService.findAll()).willReturn(mentorDtos);
        mockMvc.perform(get(MENTORS).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTORS_VIEW))
              .andExpect(model().attribute("mentors", mentors));
        verify(mentorService).findAll();
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        mockMvc.perform(get(MENTORS + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(MENTOR_DETAILS_VIEW))
              .andExpect(model().attribute("mentor", mentor1));
        verify(mentorService).findById(VALID_ID);
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(MENTORS + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).findById(INVALID_ID);
    }

    @Test
    void getSaveMentorPage_withNegativeId_shouldReturnSaveMentorPage() throws Exception {
        mockMvc.perform(get(MENTORS + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("mentorDto", new MentorDto()));
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnUpdateMentorPage() throws Exception {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        mockMvc.perform(get(MENTORS + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_MENTOR_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("mentorDto", mentorDto1));
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(MENTORS + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveMentor() throws Exception {
        mockMvc.perform(post(MENTORS + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).save(any(MentorDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateMentorWithGivenId() throws Exception {
        mockMvc.perform(post(MENTORS + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).updateById(mentorDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(MENTORS + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(mentorDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).updateById(any(MentorDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        mockMvc.perform(get(MENTORS + "/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_MENTORS_VIEW))
              .andExpect(redirectedUrl(MENTORS));
        verify(mentorService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(anyInt());
        mockMvc.perform(get(MENTORS + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(mentorService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(MentorDto mentorDto) {
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", mentorDto.getName());
        params.add("email", mentorDto.getEmail());
        params.add("password", mentorDto.getPassword());
        params.add("mobile", mentorDto.getMobile());
        params.add("address", mentorDto.getAddress());
        params.add("birthday", mentorDto.getBirthday().toString());
        params.add("roleId", mentorDto.getRoleId().toString());
        params.add("employmentType", mentorDto.getEmploymentType().toString());
        params.add("position", mentorDto.getPosition().toString());
        params.add("grade", mentorDto.getGrade().toString());
        params.add("supervisingMentorId", supervisingMentorId != null ? mentorDto.getSupervisingMentorId().toString() : null);
        params.add("studiesIds", mentorDto.getStudiesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("experiencesIds", mentorDto.getExperiencesIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        params.add("nrTrainees", mentorDto.getNrTrainees().toString());
        params.add("maxTrainees", mentorDto.getMaxTrainees().toString());
        params.add("isTrainingOpen", mentorDto.getIsTrainingOpen().toString());
        return params;
    }

    private MentorDto convertToDto(Mentor mentor) {
        Mentor supervisingMentor = mentor.getSupervisingMentor();
        return MentorDto.builder()
              .id(mentor.getId())
              .name(mentor.getName())
              .email(mentor.getEmail())
              .password(mentor.getPassword())
              .mobile(mentor.getMobile())
              .address(mentor.getAddress())
              .birthday(mentor.getBirthday())
              .roleId(mentor.getRole().getId())
              .employmentType(mentor.getEmploymentType())
              .position(mentor.getPosition())
              .grade(mentor.getGrade())
              .supervisingMentorId(supervisingMentor != null ? supervisingMentor.getId() : null)
              .studiesIds(mentor.getStudies().stream().map(Study::getId).toList())
              .experiencesIds(mentor.getExperiences().stream().map(Experience::getId).toList())
              .nrTrainees(mentor.getNrTrainees())
              .maxTrainees(mentor.getMaxTrainees())
              .isTrainingOpen(mentor.getIsTrainingOpen())
              .build();
    }
}
