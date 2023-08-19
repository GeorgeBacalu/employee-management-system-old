package com.project.ems.integration.mentor;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRepository;
import com.project.ems.mentor.MentorServiceImpl;
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

import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentorDto1;
import static com.project.ems.mock.MentorMock.getMockedMentorDto2;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosPage1;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosPage2;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosPage3;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage1;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage2;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage3;
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
class MentorServiceIntegrationTest {

    @Autowired
    private MentorServiceImpl mentorService;

    @MockBean
    private MentorRepository mentorRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private StudyService studyService;

    @MockBean
    private ExperienceService experienceService;

    @Captor
    private ArgumentCaptor<Mentor> mentorCaptor;

    private Mentor mentor1;
    private Mentor mentor2;
    private List<Mentor> mentors;
    private Employee employee;
    private Role role;
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
        employee = getMockedEmployee1();
        role = getMockedRole2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        mentorDto1 = getMockedMentorDto1();
        mentorDto2 = getMockedMentorDto2();
        mentorDtos = mentorService.convertToDtos(mentors);
    }

    @Test
    void findAll_shouldReturnListOfMentors() {
        given(mentorRepository.findAll()).willReturn(mentors);
        List<MentorDto> result = mentorService.findAll();
        assertThat(result).isEqualTo(mentorDtos);
    }

    @Test
    void findById_withValidId_shouldReturnMentorWithGivenId() {
        given(mentorRepository.findById(anyInt())).willReturn(Optional.ofNullable(mentor1));
        MentorDto result = mentorService.findById(VALID_ID);
        assertThat(result).isEqualTo(mentorDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> mentorService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddMentorToList() {
        given(roleService.findEntityById(anyInt())).willReturn(role);
        mentorDto1.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies1.get(id - 1)));
        mentorDto1.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences1.get(id - 1)));
        given(mentorRepository.save(any(Mentor.class))).willReturn(mentor1);
        MentorDto result = mentorService.save(mentorDto1);
        verify(mentorRepository).save(mentorCaptor.capture());
        assertThat(result).isEqualTo(mentorService.convertToDto(mentorCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateMentorWithGivenId() {
        Mentor mentor = mentor2; mentor.setId(VALID_ID);
        given(mentorRepository.findById(anyInt())).willReturn(Optional.ofNullable(mentor1));
        given(roleService.findEntityById(anyInt())).willReturn(role);
        mentorDto2.getStudiesIds().forEach(id -> given(studyService.findEntityById(id)).willReturn(studies2.get(id - 3)));
        mentorDto2.getExperiencesIds().forEach(id -> given(experienceService.findEntityById(id)).willReturn(experiences2.get(id - 3)));
        given(mentorRepository.save(any(Mentor.class))).willReturn(mentor);
        MentorDto result = mentorService.updateById(mentorDto2, VALID_ID);
        verify(mentorRepository).save(mentorCaptor.capture());
        assertThat(result).isEqualTo(mentorService.convertToDto(mentorCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> mentorService.updateById(mentorDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
        verify(mentorRepository, never()).save(any(Mentor.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() {
        given(mentorRepository.findById(anyInt())).willReturn(Optional.ofNullable(mentor1));
        given(mentorRepository.findAllBySupervisingMentor(any(Mentor.class))).willReturn(List.of(mentor2));
        given(employeeRepository.findAllByMentor(any(Mentor.class))).willReturn(List.of(employee));
        mentorService.deleteById(VALID_ID);
        verify(mentorRepository).delete(mentor1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> mentorService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(MENTOR_NOT_FOUND, INVALID_ID));
        verify(mentorRepository, never()).delete(any(Mentor.class));
    }

    private Stream<Arguments> paginationArguments() {
        Page<Mentor> mentorsPage1 = new PageImpl<>(getMockedMentorsPage1());
        Page<Mentor> mentorsPage2 = new PageImpl<>(getMockedMentorsPage2());
        Page<Mentor> mentorsPage3 = new PageImpl<>(getMockedMentorsPage3());
        Page<Mentor> emptyPage = new PageImpl<>(Collections.emptyList());
        Page<MentorDto> mentorDtosPage1 = new PageImpl<>(getMockedMentorDtosPage1());
        Page<MentorDto> mentorDtosPage2 = new PageImpl<>(getMockedMentorDtosPage2());
        Page<MentorDto> mentorDtosPage3 = new PageImpl<>(getMockedMentorDtosPage3());
        Page<MentorDto> emptyDtoPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", MENTOR_FILTER_KEY, mentorsPage1, mentorDtosPage1),
                         Arguments.of(1, 2, "id", MENTOR_FILTER_KEY, mentorsPage2, mentorDtosPage2),
                         Arguments.of(2, 2, "id", MENTOR_FILTER_KEY, emptyPage, emptyDtoPage),
                         Arguments.of(0, 2, "id", "", mentorsPage1, mentorDtosPage1),
                         Arguments.of(1, 2, "id", "", mentorsPage2, mentorDtosPage2),
                         Arguments.of(2, 2, "id", "", mentorsPage3, mentorDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String key, Page<Mentor> entityPage, Page<MentorDto> dtoPage) {
        if(key.trim().equals("")) {
            given(mentorRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(mentorRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(entityPage);
        }
        Page<MentorDto> result = mentorService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
