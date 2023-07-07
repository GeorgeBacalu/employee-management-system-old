package com.project.ems.unit.study;

import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyRestController;
import com.project.ems.study.StudyService;
import com.project.ems.wrapper.PageWrapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage1;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage2;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage3;
import static com.project.ems.mock.StudyMock.getMockedStudy1;
import static com.project.ems.mock.StudyMock.getMockedStudy2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudyRestControllerTest {

    @InjectMocks
    private StudyRestController studyRestController;

    @Mock
    private StudyService studyService;

    @Spy
    private ModelMapper modelMapper;

    private StudyDto studyDto1;
    private StudyDto studyDto2;
    private List<StudyDto> studyDtos;
    private List<StudyDto> studyDtosPage1;
    private List<StudyDto> studyDtosPage2;
    private List<StudyDto> studyDtosPage3;

    @BeforeEach
    void setUp() {
        studyDto1 = convertToDto(modelMapper, getMockedStudy1());
        studyDto2 = convertToDto(modelMapper, getMockedStudy2());
        studyDtos = convertToDtoList(modelMapper, getMockedStudies());
        studyDtosPage1 = convertToDtoList(modelMapper, getMockedStudiesPage1());
        studyDtosPage2 = convertToDtoList(modelMapper, getMockedStudiesPage2());
        studyDtosPage3 = convertToDtoList(modelMapper, getMockedStudiesPage3());
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

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfStudiesFilteredByKeyPage1() {
        PageImpl<StudyDto> filteredStudyDtosPage = new PageImpl<>(studyDtosPage1);
        given(studyService.findAllByKey(pageable, STUDY_FILTER_KEY)).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageable, STUDY_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfStudiesFilteredByKeyPage2() {
        PageImpl<StudyDto> filteredStudyDtosPage = new PageImpl<>(studyDtosPage2);
        given(studyService.findAllByKey(pageable2, STUDY_FILTER_KEY)).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageable2, STUDY_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfStudiesFilteredByKeyPage3() {
        PageImpl<StudyDto> filteredStudyDtosPage = new PageImpl<>(Collections.emptyList());
        given(studyService.findAllByKey(pageable3, STUDY_FILTER_KEY)).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageable3, STUDY_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfStudiesPage1() {
        PageImpl<StudyDto> filteredStudyDtosPage = new PageImpl<>(studyDtosPage1);
        given(studyService.findAllByKey(pageable, "")).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageable, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfStudiesPage2() {
        PageImpl<StudyDto> filteredStudyDtosPage = new PageImpl<>(studyDtosPage2);
        given(studyService.findAllByKey(pageable2, "")).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageable2, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfStudiesPage3() {
        PageImpl<StudyDto> filteredStudyDtosPage = new PageImpl<>(studyDtosPage3);
        given(studyService.findAllByKey(pageable3, "")).willReturn(filteredStudyDtosPage);
        ResponseEntity<PageWrapper<StudyDto>> response = studyRestController.findAllByKey(pageable3, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredStudyDtosPage.getContent()));
    }
}
