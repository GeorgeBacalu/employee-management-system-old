package com.project.ems.unit.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceController;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceControllerTest {

    @InjectMocks
    private ExperienceController experienceController;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;
    
    private Experience experience;
    private List<Experience> experiences;
    private ExperienceDto experienceDto;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience = getMockedExperience1();
        experiences = getMockedExperiences();
        experienceDto = convertToDto(modelMapper, experience);
        experienceDtos = convertToDtoList(modelMapper, experiences);
    }

    @Test
    void getAllExperiencesPage_shouldReturnExperiencesPage() {
        given(experienceService.findAll()).willReturn(experienceDtos);
        given(model.getAttribute(anyString())).willReturn(experiences);
        String viewName = experienceController.getAllExperiencesPage(model);
        assertThat(viewName).isEqualTo(EXPERIENCES_VIEW);
        assertThat(model.getAttribute("experiences")).isEqualTo(experiences);
    }

    @Test
    void getExperienceByIdPage_withValidId_shouldReturnExperienceDetailsPage() {
        given(experienceService.findById(anyInt())).willReturn(experienceDto);
        given(model.getAttribute(anyString())).willReturn(experience);
        String viewName = experienceController.getExperienceByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(EXPERIENCE_DETAILS_VIEW);
        assertThat(model.getAttribute("experience")).isEqualTo(experience);
    }

    @Test
    void getExperienceByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> experienceController.getExperienceByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveExperiencePage_withNegativeId_shouldReturnSaveExperiencePage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("experienceDto")).willReturn(new ExperienceDto());
        String viewName = experienceController.getSaveExperiencePage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("experienceDto")).isEqualTo(new ExperienceDto());
    }

    @Test
    void getSaveExperiencePage_withValidId_shouldReturnUpdateExperiencePage() {
        given(experienceService.findById(anyInt())).willReturn(experienceDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("experienceDto")).willReturn(experienceDto);
        String viewName = experienceController.getSaveExperiencePage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_EXPERIENCE_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("experienceDto")).isEqualTo(experienceDto);
    }

    @Test
    void getSaveExperiencePage_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> experienceController.getSaveExperiencePage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveExperience() {
        String viewName = experienceController.save(experienceDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).save(experienceDto);
    }

    @Test
    void save_withValidId_shouldUpdateExperienceWithGivenId() {
        String viewName = experienceController.save(experienceDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).updateById(experienceDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(experienceDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> experienceController.save(experienceDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenIdFromList() {
        String viewName = experienceController.deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_EXPERIENCES_VIEW);
        verify(experienceService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> experienceController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
