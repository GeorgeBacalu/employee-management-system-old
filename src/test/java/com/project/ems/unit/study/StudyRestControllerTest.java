package com.project.ems.unit.study;

import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
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
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudyDto1;
import static com.project.ems.mock.StudyMock.getMockedStudyDto2;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage1;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage2;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyRestControllerTest {

    @InjectMocks
    private StudyRestController studyRestController;

    @Mock
    private StudyService studyService;

    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;

    @BeforeEach
    void setUp() {
        studyDto1 = getMockedStudyDto1();
        studyDto2 = getMockedStudyDto2();
        studyDtos = studyService.convertToDtos(getMockedStudies());
    }

    @Test
    void findAll_shouldReturnListOfStudies() {
        given(studyService.findAll()).willReturn(studyDtos);
        ResponseEntity<List<StudyDto>> response = studyRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studyDtos);
    }

    @Test
    void findById_shouldReturnStudyWithGivenId() {
        given(studyService.findById(anyInt())).willReturn(studyDto1);
        ResponseEntity<StudyDto> response = studyRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studyDto1);
    }

    @Test
    void save_shouldAddStudyToList() {
        given(studyService.save(any(StudyDto.class))).willReturn(studyDto1);
        ResponseEntity<StudyDto> response = studyRestController.save(studyDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(studyDto1);
    }

    @Test
    void updateById_shouldUpdateStudyWithGivenId() {
        StudyDto studyDto = studyDto2; studyDto.setId(VALID_ID);
        given(studyService.updateById(any(StudyDto.class), anyInt())).willReturn(studyDto);
        ResponseEntity<StudyDto> response = studyRestController.updateById(studyDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studyDto);
    }

    @Test
    void deleteById_shouldRemoveStudyWithGivenIdFromList() {
        ResponseEntity<Void> response = studyRestController.deleteById(VALID_ID);
        verify(studyService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @ParameterizedTest
    @CsvSource({ "1, ${STUDY_FILTER_KEY}", "2, ${STUDY_FILTER_KEY}", "3, ${STUDY_FILTER_KEY}", "1, ''", "2, ''", "3, ''"  })
    void findAllByKey_shouldReturnListOfStudiesFilteredByKey(int page, String key) {
        Pair<List<StudyDto>, Pageable> pair = switch(page) {
            case 1 -> Pair.of(getMockedStudyDtosPage1(), pageable);
            case 2 -> Pair.of(getMockedStudyDtosPage2(), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : getMockedStudyDtosPage3(), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
        Page<StudyDto> filteredStudyDtosPage = new PageImpl<>(pair.getLeft());
        given(studyService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pair.getRight(), key);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }
}
