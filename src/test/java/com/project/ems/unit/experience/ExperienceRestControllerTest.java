package com.project.ems.unit.experience;

import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceRestController;
import com.project.ems.experience.ExperienceService;
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
import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceRestControllerTest {

    @InjectMocks
    private ExperienceRestController experienceRestController;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private ModelMapper modelMapper;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;
    private List<ExperienceDto> experienceDtosPage1;
    private List<ExperienceDto> experienceDtosPage2;
    private List<ExperienceDto> experienceDtosPage3;

    @BeforeEach
    void setUp() {
        experienceDto1 = convertToDto(modelMapper, getMockedExperience1());
        experienceDto2 = convertToDto(modelMapper, getMockedExperience2());
        experienceDtos = convertToDtoList(modelMapper, getMockedExperiences());
        experienceDtosPage1 = convertToDtoList(modelMapper, getMockedExperiencesPage1());
        experienceDtosPage2 = convertToDtoList(modelMapper, getMockedExperiencesPage2());
        experienceDtosPage3 = convertToDtoList(modelMapper, getMockedExperiencesPage3());
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

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfExperiencesFilteredByKeyPage1() {
        PageImpl<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(experienceDtosPage1);
        given(experienceService.findAllByKey(pageable, EXPERIENCE_FILTER_KEY)).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pageable, EXPERIENCE_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfExperiencesFilteredByKeyPage2() {
        PageImpl<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(experienceDtosPage2);
        given(experienceService.findAllByKey(pageable2, EXPERIENCE_FILTER_KEY)).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pageable2, EXPERIENCE_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfExperiencesFilteredByKeyPage3() {
        PageImpl<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(Collections.emptyList());
        given(experienceService.findAllByKey(pageable3, EXPERIENCE_FILTER_KEY)).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pageable3, EXPERIENCE_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfExperiencesPage1() {
        PageImpl<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(experienceDtosPage1);
        given(experienceService.findAllByKey(pageable, "")).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pageable, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfExperiencesPage2() {
        PageImpl<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(experienceDtosPage2);
        given(experienceService.findAllByKey(pageable2, "")).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pageable2, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfExperiencesPage3() {
        PageImpl<ExperienceDto> filteredExperienceDtosPage = new PageImpl<>(experienceDtosPage3);
        given(experienceService.findAllByKey(pageable3, "")).willReturn(filteredExperienceDtosPage);
        ResponseEntity<PageWrapper<ExperienceDto>> response = experienceRestController.findAllByKey(pageable3, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredExperienceDtosPage.getContent()));
    }
}
