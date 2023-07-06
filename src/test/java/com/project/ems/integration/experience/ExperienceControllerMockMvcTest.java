package com.project.ems.integration.experience;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceController;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.ExperienceService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.EXPERIENCES;
import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.EXPERIENCE_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_EXPERIENCES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_EXPERIENCE_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience10;
import static com.project.ems.mock.ExperienceMock.getMockedExperience11;
import static com.project.ems.mock.ExperienceMock.getMockedExperience12;
import static com.project.ems.mock.ExperienceMock.getMockedExperience13;
import static com.project.ems.mock.ExperienceMock.getMockedExperience14;
import static com.project.ems.mock.ExperienceMock.getMockedExperience15;
import static com.project.ems.mock.ExperienceMock.getMockedExperience16;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperience3;
import static com.project.ems.mock.ExperienceMock.getMockedExperience4;
import static com.project.ems.mock.ExperienceMock.getMockedExperience5;
import static com.project.ems.mock.ExperienceMock.getMockedExperience6;
import static com.project.ems.mock.ExperienceMock.getMockedExperience7;
import static com.project.ems.mock.ExperienceMock.getMockedExperience8;
import static com.project.ems.mock.ExperienceMock.getMockedExperience9;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ExperienceController.class)
class ExperienceControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExperienceService experienceService;

    @MockBean
    private ModelMapper modelMapper;

    private Experience experience1;
    private Experience experience2;
    private Experience experience3;
    private Experience experience4;
    private Experience experience5;
    private Experience experience6;
    private Experience experience7;
    private Experience experience8;
    private Experience experience9;
    private Experience experience10;
    private Experience experience11;
    private Experience experience12;
    private Experience experience13;
    private Experience experience14;
    private Experience experience15;
    private Experience experience16;
    private List<Experience> experiences;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private ExperienceDto experienceDto3;
    private ExperienceDto experienceDto4;
    private ExperienceDto experienceDto5;
    private ExperienceDto experienceDto6;
    private ExperienceDto experienceDto7;
    private ExperienceDto experienceDto8;
    private ExperienceDto experienceDto9;
    private ExperienceDto experienceDto10;
    private ExperienceDto experienceDto11;
    private ExperienceDto experienceDto12;
    private ExperienceDto experienceDto13;
    private ExperienceDto experienceDto14;
    private ExperienceDto experienceDto15;
    private ExperienceDto experienceDto16;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experience1 = getMockedExperience1();
        experience2 = getMockedExperience2();
        experience3 = getMockedExperience3();
        experience4 = getMockedExperience4();
        experience5 = getMockedExperience5();
        experience6 = getMockedExperience6();
        experience7 = getMockedExperience7();
        experience8 = getMockedExperience8();
        experience9 = getMockedExperience9();
        experience10 = getMockedExperience10();
        experience11 = getMockedExperience11();
        experience12 = getMockedExperience12();
        experience13 = getMockedExperience13();
        experience14 = getMockedExperience14();
        experience15 = getMockedExperience15();
        experience16 = getMockedExperience16();
        experiences = getMockedExperiences();
        experienceDto1 = convertToDto(experience1);
        experienceDto2 = convertToDto(experience2);
        experienceDto3 = convertToDto(experience3);
        experienceDto4 = convertToDto(experience4);
        experienceDto5 = convertToDto(experience5);
        experienceDto6 = convertToDto(experience6);
        experienceDto7 = convertToDto(experience7);
        experienceDto8 = convertToDto(experience8);
        experienceDto9 = convertToDto(experience9);
        experienceDto10 = convertToDto(experience10);
        experienceDto11 = convertToDto(experience11);
        experienceDto12 = convertToDto(experience12);
        experienceDto13 = convertToDto(experience13);
        experienceDto14 = convertToDto(experience14);
        experienceDto15 = convertToDto(experience15);
        experienceDto16 = convertToDto(experience16);
        experienceDtos = List.of(experienceDto1, experienceDto2, experienceDto3, experienceDto4, experienceDto5, experienceDto6, experienceDto7, experienceDto8,
                                 experienceDto9, experienceDto10, experienceDto11, experienceDto12, experienceDto13, experienceDto14, experienceDto15, experienceDto16);

        given(modelMapper.map(experienceDto1, Experience.class)).willReturn(experience1);
        given(modelMapper.map(experienceDto2, Experience.class)).willReturn(experience2);
        given(modelMapper.map(experienceDto3, Experience.class)).willReturn(experience3);
        given(modelMapper.map(experienceDto4, Experience.class)).willReturn(experience4);
        given(modelMapper.map(experienceDto5, Experience.class)).willReturn(experience5);
        given(modelMapper.map(experienceDto6, Experience.class)).willReturn(experience6);
        given(modelMapper.map(experienceDto7, Experience.class)).willReturn(experience7);
        given(modelMapper.map(experienceDto8, Experience.class)).willReturn(experience8);
        given(modelMapper.map(experienceDto9, Experience.class)).willReturn(experience9);
        given(modelMapper.map(experienceDto10, Experience.class)).willReturn(experience10);
        given(modelMapper.map(experienceDto11, Experience.class)).willReturn(experience11);
        given(modelMapper.map(experienceDto12, Experience.class)).willReturn(experience12);
        given(modelMapper.map(experienceDto13, Experience.class)).willReturn(experience13);
        given(modelMapper.map(experienceDto14, Experience.class)).willReturn(experience14);
        given(modelMapper.map(experienceDto15, Experience.class)).willReturn(experience15);
        given(modelMapper.map(experienceDto16, Experience.class)).willReturn(experience16);
    }

    @Test
    void getAllExperiencesPage_shouldReturnExperiencesPage() throws Exception {
        given(experienceService.findAll()).willReturn(experienceDtos);
        mockMvc.perform(get(EXPERIENCES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCES_VIEW))
              .andExpect(model().attribute("experiences", experiences));
        verify(experienceService).findAll();
    }

    @Test
    void getExperienceByIdPage_withValidId_shouldReturnExperienceDetailsPage() throws Exception {
        given(experienceService.findById(anyInt())).willReturn(experienceDto1);
        mockMvc.perform(get(EXPERIENCES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(EXPERIENCE_DETAILS_VIEW))
              .andExpect(model().attribute("experience", experience1));
        verify(experienceService).findById(VALID_ID);
    }

    @Test
    void getExperienceByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EXPERIENCES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).findById(INVALID_ID);
    }

    @Test
    void getSaveExperiencePage_withNegativeId_shouldReturnSaveExperiencePage() throws Exception {
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("experienceDto", new ExperienceDto()));
    }

    @Test
    void getSaveExperiencePage_withValidId_shouldReturnUpdateExperiencePage() throws Exception {
        given(experienceService.findById(anyInt())).willReturn(experienceDto1);
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_EXPERIENCE_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("experienceDto", experienceDto1));
    }

    @Test
    void getSaveExperiencePage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(EXPERIENCES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveExperience() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(experienceDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).save(any(ExperienceDto.class));
    }

    @Test
    void save_withValidId_shouldUpdateExperienceWithGivenId() throws Exception {
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(experienceDto1)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).updateById(experienceDto1, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        given(experienceService.updateById(any(ExperienceDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(EXPERIENCES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(experienceDto1)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).updateById(any(ExperienceDto.class), anyInt());
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenIdFromList() throws Exception {
        mockMvc.perform(get(EXPERIENCES + "/delete/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_EXPERIENCES_VIEW))
              .andExpect(redirectedUrl(EXPERIENCES));
        verify(experienceService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(EXPERIENCE_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(experienceService).deleteById(anyInt());
        mockMvc.perform(get(EXPERIENCES + "/delete/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(experienceService).deleteById(INVALID_ID);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(ExperienceDto experienceDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", experienceDto.getTitle());
        params.add("organization", experienceDto.getOrganization());
        params.add("description", experienceDto.getDescription());
        params.add("type", experienceDto.getType().toString());
        params.add("startedAt", experienceDto.getStartedAt().toString());
        params.add("finishedAt", experienceDto.getFinishedAt().toString());
        return params;
    }

    private ExperienceDto convertToDto(Experience experience) {
        return ExperienceDto.builder()
              .id(experience.getId())
              .title(experience.getTitle())
              .organization(experience.getOrganization())
              .description(experience.getDescription())
              .type(experience.getType())
              .startedAt(experience.getStartedAt())
              .finishedAt(experience.getFinishedAt())
              .build();
    }
}
