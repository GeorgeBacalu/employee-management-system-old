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
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
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

    @Spy
    private ModelMapper modelMapper;

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
        mentorDto1 = convertToDto(modelMapper, mentor1);
        mentorDto2 = convertToDto(modelMapper, mentor2);
        mentorDtos = convertToDtoList(modelMapper, mentors);
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

    private Stream<Arguments> paginationArguments() {
        List<Mentor> mentorsPage1 = getMockedMentorsPage1();
        List<Mentor> mentorsPage2 = getMockedMentorsPage2();
        List<Mentor> mentorsPage3 = getMockedMentorsPage3();
        List<MentorDto> mentorDtosPage1 = convertToDtoList(modelMapper, mentorsPage1);
        List<MentorDto> mentorDtosPage2 = convertToDtoList(modelMapper, mentorsPage2);
        List<MentorDto> mentorDtosPage3 = convertToDtoList(modelMapper, mentorsPage3);
        return Stream.of(Arguments.of(0, 2, "id", "asc", MENTOR_FILTER_KEY, new PageImpl<>(mentorsPage1), new PageImpl<>(mentorDtosPage1)),
              Arguments.of(1, 2, "id", "asc", MENTOR_FILTER_KEY, new PageImpl<>(mentorsPage2), new PageImpl<>(mentorDtosPage2)),
              Arguments.of(2, 2, "id", "asc", MENTOR_FILTER_KEY, new PageImpl<>(Collections.emptyList()), new PageImpl<>(Collections.emptyList())),
              Arguments.of(0, 2, "id", "asc", "", new PageImpl<>(mentorsPage1), new PageImpl<>(mentorDtosPage1)),
              Arguments.of(1, 2, "id", "asc", "", new PageImpl<>(mentorsPage2), new PageImpl<>(mentorDtosPage2)),
              Arguments.of(2, 2, "id", "asc", "", new PageImpl<>(mentorsPage3), new PageImpl<>(mentorDtosPage3)));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, PageImpl<Mentor> entityPage, PageImpl<MentorDto> dtoPage) {
        if(key.trim().equals("")) {
            given(mentorRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(mentorRepository.findAllByKey(any(Pageable.class), anyString())).willReturn(entityPage);
        }
        Page<MentorDto> result = mentorService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
