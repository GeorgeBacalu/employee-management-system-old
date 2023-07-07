package com.project.ems.unit.study;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorRepository;
import com.project.ems.study.Study;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRepository;
import com.project.ems.study.StudyServiceImpl;
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

import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.StudyMock.getMockedFilteredStudies;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyServiceImplTest {

    @InjectMocks
    private StudyServiceImpl studyService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MentorRepository mentorRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Study> studyCaptor;

    private Study study1;
    private Study study2;
    private List<Study> studies;
    private List<Study> filteredStudies;
    private Employee employee;
    private Mentor mentor;
    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;
    private List<StudyDto> filteredStudyDtos;

    @BeforeEach
    void setUp() {
        study1 = getMockedStudy1();
        study2 = getMockedStudy2();
        studies = getMockedStudies();
        filteredStudies = getMockedFilteredStudies();
        employee = getMockedEmployee1();
        mentor = getMockedMentor1();
        studyDto1 = convertToDto(modelMapper, study1);
        studyDto2 = convertToDto(modelMapper, study2);
        studyDtos = convertToDtoList(modelMapper, studies);
        filteredStudyDtos = convertToDtoList(modelMapper, filteredStudies);
    }

    @Test
    void findAll_shouldReturnListOfStudies() {
        given(studyRepository.findAll()).willReturn(studies);
        List<StudyDto> result = studyService.findAll();
        assertThat(result).isEqualTo(studyDtos);
    }

    @Test
    void findById_withValidId_shouldReturnStudyWithGivenId() {
        given(studyRepository.findById(anyInt())).willReturn(Optional.ofNullable(study1));
        StudyDto result = studyService.findById(VALID_ID);
        assertThat(result).isEqualTo(studyDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> studyService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddStudyToList() {
        given(studyRepository.save(any(Study.class))).willReturn(study1);
        StudyDto result = studyService.save(studyDto1);
        verify(studyRepository).save(studyCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, studyCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateStudyWithGivenId() {
        Study study = study2; study.setId(VALID_ID);
        given(studyRepository.findById(anyInt())).willReturn(Optional.ofNullable(study1));
        given(studyRepository.save(any(Study.class))).willReturn(study);
        StudyDto result = studyService.updateById(studyDto2, VALID_ID);
        verify(studyRepository).save(studyCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, studyCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> studyService.updateById(studyDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDY_NOT_FOUND, INVALID_ID));
        verify(studyRepository, never()).save(any(Study.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveStudyWithGivenIdFromList() {
        given(studyRepository.findById(anyInt())).willReturn(Optional.ofNullable(study1));
        given(employeeRepository.findAllByStudiesContains(any(Study.class))).willReturn(List.of(employee));
        given(mentorRepository.findAllByStudiesContains(any(Study.class))).willReturn(List.of(mentor));
        studyService.deleteById(VALID_ID);
        verify(studyRepository).delete(study1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> studyService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(STUDY_NOT_FOUND, INVALID_ID));
        verify(studyRepository, never()).delete(any(Study.class));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfStudiesPaginatedSortedAndFilteredByKey() {
        given(studyRepository.findAllByKey(pageable, STUDY_FILTER_KEY)).willReturn(new PageImpl<>(filteredStudies));
        Page<StudyDto> result = studyService.findAllByKey(pageable, STUDY_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(filteredStudyDtos);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfStudiesPaginatedAndSorted() {
        given(studyRepository.findAll(pageable)).willReturn(new PageImpl<>(studies));
        Page<StudyDto> result = studyService.findAllByKey(pageable, "");
        assertThat(result.getContent()).isEqualTo(studyDtos);
    }
}
