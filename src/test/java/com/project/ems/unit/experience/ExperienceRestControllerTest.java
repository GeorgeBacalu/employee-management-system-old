package com.project.ems.unit.experience;

import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDto1;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDto2;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDtosPage1;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDtosPage2;
import static com.project.ems.mock.ExperienceMock.getMockedExperienceDtosPage3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceRestControllerTest {

    @InjectMocks
    private ExperienceRestController experienceRestController;

    @Mock
    private ExperienceService experienceService;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experienceDto1 = getMockedExperienceDto1();
        experienceDto2 = getMockedExperienceDto2();
        experienceDtos = experienceService.convertToDtos(getMockedExperiences());
    }

    @Test
    void findAll_shouldReturnListOfExperiences() {
        given(experienceService.findAll()).willReturn(experienceDtos);
        ResponseEntity<List<ExperienceDto>> response = experienceRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDtos);
    }

    @Test
    void findById_shouldReturnExperienceWithGivenId() {
        given(experienceService.findById(anyInt())).willReturn(experienceDto1);
        ResponseEntity<ExperienceDto> response = experienceRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void save_shouldAddExperienceToList() {
        given(experienceService.save(any(ExperienceDto.class))).willReturn(experienceDto1);
        ResponseEntity<ExperienceDto> response = experienceRestController.save(experienceDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void updateById_shouldUpdateExperienceWithGivenId() {
        ExperienceDto experienceDto = experienceDto2; experienceDto.setId(VALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willReturn(experienceDto);
        ResponseEntity<ExperienceDto> response = experienceRestController.updateById(experienceDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDto);
    }

    @Test
    void deleteById_shouldRemoveExperienceWithGivenFromList() {
        ResponseEntity<Void> response = experienceRestController.deleteById(VALID_ID);
        verify(experienceService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @ParameterizedTest
    @CsvSource({ "1, ${EXPERIENCE_FILTER_KEY}", "2, ${EXPERIENCE_FILTER_KEY}", "3, ${EXPERIENCE_FILTER_KEY}", "1, ''", "2, ''", "3, ''"  })
    void findAllByKey_shouldReturnListOfExperiencesFilteredByKey(int page, String key) {
        Pair<List<ExperienceDto>, Pageable> pair = switch(page) {
            case 1 -> Pair.of(getMockedExperienceDtosPage1(), pageable);
            case 2 -> Pair.of(getMockedExperienceDtosPage2(), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : getMockedExperienceDtosPage3(), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
        Page<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(pair.getLeft());
        given(experienceService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pair.getRight(), key);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }
}
