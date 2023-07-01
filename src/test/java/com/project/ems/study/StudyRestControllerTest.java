package com.project.ems.study;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mock.StudyMock.getMockedStudies;
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

    @BeforeEach
    void setUp() {
        studyDto1 = convertToDto(modelMapper, getMockedStudy1());
        studyDto2 = convertToDto(modelMapper, getMockedStudy2());
        studyDtos = convertToDtoList(modelMapper, getMockedStudies());
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
}
