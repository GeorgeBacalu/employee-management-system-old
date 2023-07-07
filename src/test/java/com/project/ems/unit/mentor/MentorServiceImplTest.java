package com.project.ems.unit.mentor;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToDtoList;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorServiceImplTest {

    @InjectMocks
    private MentorServiceImpl mentorService;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private StudyService studyService;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Mentor> mentorCaptor;

    private Mentor mentor1;
    private Mentor mentor2;
    private List<Mentor> mentors;
    private List<Mentor> mentorsPage1;
    private List<Mentor> mentorsPage2;
    private List<Mentor> mentorsPage3;
    private Employee employee;
    private Role role;
    private List<Study> studies1;
    private List<Study> studies2;
    private List<Experience> experiences1;
    private List<Experience> experiences2;
    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;
    private List<MentorDto> mentorDtosPage1;
    private List<MentorDto> mentorDtosPage2;
    private List<MentorDto> mentorDtosPage3;

    @BeforeEach
    void setUp() {
        mentor1 = getMockedMentor1();
        mentor2 = getMockedMentor2();
        mentors = getMockedMentors();
        mentorsPage1 = getMockedMentorsPage1();
        mentorsPage2 = getMockedMentorsPage2();
        mentorsPage3 = getMockedMentorsPage3();
        employee = getMockedEmployee1();
        role = getMockedRole2();
        studies1 = getMockedStudies1();
        studies2 = getMockedStudies2();
        experiences1 = getMockedExperiences1();
        experiences2 = getMockedExperiences2();
        mentorDto1 = convertToDto(modelMapper, mentor1);
        mentorDto2 = convertToDto(modelMapper, mentor2);
        mentorDtos = convertToDtoList(modelMapper, mentors);
        mentorDtosPage1 = convertToDtoList(modelMapper, mentorsPage1);
        mentorDtosPage2 = convertToDtoList(modelMapper, mentorsPage2);
        mentorDtosPage3 = convertToDtoList(modelMapper, mentorsPage3);
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
        assertThat(result).isEqualTo(convertToDto(modelMapper, mentorCaptor.getValue()));
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
        assertThat(result).isEqualTo(convertToDto(modelMapper, mentorCaptor.getValue()));
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

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfMentorsFilteredByKeyPage1() {
        given(mentorRepository.findAllByKey(pageable, MENTOR_FILTER_KEY)).willReturn(new PageImpl<>(mentorsPage1));
        Page<MentorDto> result = mentorService.findAllByKey(pageable, MENTOR_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(mentorDtosPage1);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfMentorsFilteredByKeyPage2() {
        given(mentorRepository.findAllByKey(pageable2, MENTOR_FILTER_KEY)).willReturn(new PageImpl<>(mentorsPage2));
        Page<MentorDto> result = mentorService.findAllByKey(pageable2, MENTOR_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(mentorDtosPage2);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfMentorsFilteredByKeyPage3() {
        given(mentorRepository.findAllByKey(pageable3, MENTOR_FILTER_KEY)).willReturn(new PageImpl<>(Collections.emptyList()));
        Page<MentorDto> result = mentorService.findAllByKey(pageable3, MENTOR_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(Collections.emptyList());
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfMentorsPage1() {
        given(mentorRepository.findAll(pageable)).willReturn(new PageImpl<>(mentorsPage1));
        Page<MentorDto> result = mentorService.findAllByKey(pageable, "");
        assertThat(result.getContent()).isEqualTo(mentorDtosPage1);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfMentorsPage2() {
        given(mentorRepository.findAll(pageable2)).willReturn(new PageImpl<>(mentorsPage2));
        Page<MentorDto> result = mentorService.findAllByKey(pageable2, "");
        assertThat(result.getContent()).isEqualTo(mentorDtosPage2);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfMentorsPage3() {
        given(mentorRepository.findAll(pageable3)).willReturn(new PageImpl<>(mentorsPage3));
        Page<MentorDto> result = mentorService.findAllByKey(pageable3, "");
        assertThat(result.getContent()).isEqualTo(mentorDtosPage3);
    }
}
