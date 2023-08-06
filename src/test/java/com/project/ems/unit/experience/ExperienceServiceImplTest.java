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
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
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
import static org.mockito.ArgumentMatchers.eq;
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
    private Employee employee;
    private Mentor mentor;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experiences = getMockedExperiences();
        employee = getMockedEmployee1();
        mentor = getMockedMentor1();
        experienceDto1 = convertToDto(modelMapper, experience1);
        experienceDto2 = convertToDto(modelMapper, experience2);
        experienceDtos = convertToDtoList(modelMapper, experiences);
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

    @ParameterizedTest
    @CsvSource({ "1, ${EXPERIENCE_FILTER_KEY}", "2, ${EXPERIENCE_FILTER_KEY}", "3, ${EXPERIENCE_FILTER_KEY}", "1, ''", "2, ''", "3, ''"  })
    void findAllByKey_shouldReturnListOfExperiencesFilteredByKey(int page, String key) {
        Pair<List<Experience>, Pageable> pair = getFilteredExperiencesAndPageable(page, key);
        Page<Experience> filteredExperiencesPage = new PageImpl<>(pair.getLeft());
        if(key.equals("")) {
            given(experienceRepository.findAll(any(Pageable.class))).willReturn(filteredExperiencesPage);
        } else {
            given(experienceRepository.findAllByKey(any(Pageable.class), eq(key.toLowerCase()))).willReturn(filteredExperiencesPage);
        }
        Page<ExperienceDto> result = experienceService.findAllByKey(pair.getRight(), key);
        assertThat(result.getContent()).isEqualTo(convertToDtoList(modelMapper, pair.getLeft()));
    }

    private Pair<List<Experience>, Pageable> getFilteredExperiencesAndPageable(int page, String key) {
        return switch(page) {
            case 1 -> Pair.of(getMockedExperiencesPage1(), pageable);
            case 2 -> Pair.of(getMockedExperiencesPage2(), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : getMockedExperiencesPage3(), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
    }
}
