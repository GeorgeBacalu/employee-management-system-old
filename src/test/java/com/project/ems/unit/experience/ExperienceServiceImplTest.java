package com.project.ems.unit.experience;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRepository;
import com.project.ems.experience.ExperienceServiceImpl;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorRepository;
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

import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mock.EmployeeMock.getMockedEmployee1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage3;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceImplTest {

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MentorRepository mentorRepository;

    @Spy
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Experience> experienceCaptor;

    private Experience experience1;
    private Experience experience2;
    private List<Experience> experiences;
    private List<Experience> experiencesPage1;
    private List<Experience> experiencesPage2;
    private List<Experience> experiencesPage3;
    private Employee employee;
    private Mentor mentor;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;
    private List<ExperienceDto> experienceDtosPage1;
    private List<ExperienceDto> experienceDtosPage2;
    private List<ExperienceDto> experienceDtosPage3;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experiences = getMockedExperiences();
        experiencesPage1 = getMockedExperiencesPage1();
        experiencesPage2 = getMockedExperiencesPage2();
        experiencesPage3 = getMockedExperiencesPage3();
        employee = getMockedEmployee1();
        mentor = getMockedMentor1();
        experienceDto1 = convertToDto(modelMapper, experience1);
        experienceDto2 = convertToDto(modelMapper, experience2);
        experienceDtos = convertToDtoList(modelMapper, experiences);
        experienceDtosPage1 = convertToDtoList(modelMapper, experiencesPage1);
        experienceDtosPage2 = convertToDtoList(modelMapper, experiencesPage2);
        experienceDtosPage3 = convertToDtoList(modelMapper, experiencesPage3);
    }

    @Test
    void findAll_shouldReturnListOfExperiences() {
        given(experienceRepository.findAll()).willReturn(experiences);
        List<ExperienceDto> result = experienceService.findAll();
        assertThat(result).isEqualTo(experienceDtos);
    }

    @Test
    void findById_withValidId_shouldReturnExperienceWithGivenId() {
        given(experienceRepository.findById(anyInt())).willReturn(Optional.ofNullable(experience1));
        ExperienceDto result = experienceService.findById(VALID_ID);
        assertThat(result).isEqualTo(experienceDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> experienceService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddExperienceToList() {
        given(experienceRepository.save(any(Experience.class))).willReturn(experience1);
        ExperienceDto result = experienceService.save(experienceDto1);
        verify(experienceRepository).save(experienceCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, experienceCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateExperienceWithGivenId() {
        Experience experience = experience2; experience.setId(VALID_ID);
        given(experienceRepository.findById(anyInt())).willReturn(Optional.ofNullable(experience1));
        given(experienceRepository.save(any(Experience.class))).willReturn(experience);
        ExperienceDto result = experienceService.updateById(experienceDto2, VALID_ID);
        verify(experienceRepository).save(experienceCaptor.capture());
        assertThat(result).isEqualTo(convertToDto(modelMapper, experienceCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> experienceService.updateById(experienceDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
        verify(experienceRepository, never()).save(any(Experience.class));
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenFromList() {
        given(experienceRepository.findById(anyInt())).willReturn(Optional.ofNullable(experience1));
        given(employeeRepository.findAllByExperiencesContains(any(Experience.class))).willReturn(List.of(employee));
        given(mentorRepository.findAllByExperiencesContains(any(Experience.class))).willReturn(List.of(mentor));
        experienceService.deleteById(VALID_ID);
        verify(experienceRepository).delete(experience1);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> experienceService.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(EXPERIENCE_NOT_FOUND, INVALID_ID));
        verify(experienceRepository, never()).delete(any(Experience.class));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfExperiencesFilteredByKeyPage1() {
        given(experienceRepository.findAllByKey(pageable, EXPERIENCE_FILTER_KEY)).willReturn(new PageImpl<>(experiencesPage1));
        Page<ExperienceDto> result = experienceService.findAllByKey(pageable, EXPERIENCE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(experienceDtosPage1);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfExperiencesFilteredByKeyPage2() {
        given(experienceRepository.findAllByKey(pageable2, EXPERIENCE_FILTER_KEY)).willReturn(new PageImpl<>(experiencesPage2));
        Page<ExperienceDto> result = experienceService.findAllByKey(pageable2, EXPERIENCE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(experienceDtosPage2);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfExperiencesFilteredByKeyPage3() {
        given(experienceRepository.findAllByKey(pageable3, EXPERIENCE_FILTER_KEY)).willReturn(new PageImpl<>(Collections.emptyList()));
        Page<ExperienceDto> result = experienceService.findAllByKey(pageable3, EXPERIENCE_FILTER_KEY);
        assertThat(result.getContent()).isEqualTo(Collections.emptyList());
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfExperiencesPage1() {
        given(experienceRepository.findAll(pageable)).willReturn(new PageImpl<>(experiencesPage1));
        Page<ExperienceDto> result = experienceService.findAllByKey(pageable, "");
        assertThat(result.getContent()).isEqualTo(experienceDtosPage1);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfExperiencesPage2() {
        given(experienceRepository.findAll(pageable2)).willReturn(new PageImpl<>(experiencesPage2));
        Page<ExperienceDto> result = experienceService.findAllByKey(pageable2, "");
        assertThat(result.getContent()).isEqualTo(experienceDtosPage2);
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfExperiencesPage3() {
        given(experienceRepository.findAll(pageable3)).willReturn(new PageImpl<>(experiencesPage3));
        Page<ExperienceDto> result = experienceService.findAllByKey(pageable3, "");
        assertThat(result.getContent()).isEqualTo(experienceDtosPage3);
    }
}
