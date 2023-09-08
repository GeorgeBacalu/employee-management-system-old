package com.project.ems.integration.study;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorRepository;
import com.project.ems.study.Study;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRepository;
import com.project.ems.study.StudyServiceImpl;
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

import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage1;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage2;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage3;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
import static com.project.ems.mock.StudyMock.getMockedStudyDto1;
import static com.project.ems.mock.StudyMock.getMockedStudyDto2;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage1;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage2;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage3;
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
class StudyServiceIntegrationTest {

    @Autowired
    private StudyServiceImpl studyService;

    @MockBean
    private StudyRepository studyRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private MentorRepository mentorRepository;

    @Captor
    private ArgumentCaptor<Study> studyCaptor;

    private Study study1;
    private Study study2;
    private List<Study> studies;
    private Employee employee;
    private Mentor mentor;
    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        study1 = getMockedStudy1();
        study2 = getMockedStudy2();
        studies = getMockedStudies();
        employee = getMockedEmployee1();
        mentor = getMockedMentor1();
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = studyService.convertToDtos(studies);
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
        assertThat(result).isEqualTo(studyService.convertToDto(studyCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateStudyWithGivenId() {
        Study study = study2; study.setId(VALID_ID);
        given(studyRepository.findById(anyInt())).willReturn(Optional.ofNullable(study1));
        given(studyRepository.save(any(Study.class))).willReturn(study);
        StudyDto result = studyService.updateById(studyDto2, VALID_ID);
        verify(studyRepository).save(studyCaptor.capture());
        assertThat(result).isEqualTo(studyService.convertToDto(studyCaptor.getValue()));
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

    private Stream<Arguments> paginationArguments() {
        Page<Study> studiesPage1 = new PageImpl<>(getMockedStudiesPage1());
        Page<Study> studiesPage2 = new PageImpl<>(getMockedStudiesPage2());
        Page<Study> studiesPage3 = new PageImpl<>(getMockedStudiesPage3());
        Page<Study> emptyPage = new PageImpl<>(Collections.emptyList());
        Page<StudyDto> studyDtosPage1 = new PageImpl<>(getMockedStudyDtosPage1());
        Page<StudyDto> studyDtosPage2 = new PageImpl<>(getMockedStudyDtosPage2());
        Page<StudyDto> studyDtosPage3 = new PageImpl<>(getMockedStudyDtosPage3());
        Page<StudyDto> emptyDtoPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", STUDY_FILTER_KEY, studiesPage1, studyDtosPage1),
                         Arguments.of(1, 2, "id", STUDY_FILTER_KEY, studiesPage2, studyDtosPage2),
                         Arguments.of(2, 2, "id", STUDY_FILTER_KEY, emptyPage, emptyDtoPage),
                         Arguments.of(0, 2, "id", "", studiesPage1, studyDtosPage1),
                         Arguments.of(1, 2, "id", "", studiesPage2, studyDtosPage2),
                         Arguments.of(2, 2, "id", "", studiesPage3, studyDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String key, Page<Study> entityPage, Page<StudyDto> dtoPage) {
        if(key.trim().equals("")) {
            given(studyRepository.findAll(any(Pageable.class))).willReturn(entityPage);
        } else {
            given(studyRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(entityPage);
        }
        Page<StudyDto> result = studyService.findAllByKey(PageRequest.of(page, size, Sort.Direction.ASC, sortField), key);
        assertThat(result.getContent()).isEqualTo(dtoPage.getContent());
    }
}
