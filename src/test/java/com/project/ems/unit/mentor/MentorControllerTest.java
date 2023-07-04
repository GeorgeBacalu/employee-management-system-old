package com.project.ems.unit.mentor;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorController;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
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

import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.MENTOR_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_MENTORS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_MENTOR_VIEW;
import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToDtoList;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorControllerTest {

    @InjectMocks
    private MentorController mentorController;

    @Mock
    private MentorService mentorService;

    @Mock
    private RoleService roleService;

    @Mock
    private StudyService studyService;

    @Mock
    private ExperienceService experienceService;

    @Spy
    private Model model;

    @Spy
    private ModelMapper modelMapper;

    private Mentor mentor;
    private List<Mentor> mentors;
    private MentorDto mentorDto;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentor = getMockedMentor1();
        mentors = getMockedMentors();
        mentorDto = convertToDto(modelMapper, mentor);
        mentorDtos = convertToDtoList(modelMapper, mentors);
    }

    @Test
    void getAllMentorsPage_shouldReturnMentorsPage() {
        given(mentorService.findAll()).willReturn(mentorDtos);
        given(model.getAttribute(anyString())).willReturn(mentors);
        String viewName = mentorController.getAllMentorsPage(model);
        assertThat(viewName).isEqualTo(MENTORS_VIEW);
        assertThat(model.getAttribute("mentors")).isEqualTo(mentors);
    }

    @Test
    void getMentorByIdPage_withValidId_shouldReturnMentorDetailsPage() {
        given(mentorService.findById(anyInt())).willReturn(mentorDto);
        given(model.getAttribute(anyString())).willReturn(mentor);
        String viewName = mentorController.getMentorByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(MENTOR_DETAILS_VIEW);
        assertThat(model.getAttribute("mentor")).isEqualTo(mentor);
    }

    @Test
    void getMentorByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> mentorController.getMentorByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveMentorPage_withNegativeId_shouldReturnSaveMentorPage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("mentorDto")).willReturn(new MentorDto());
        String viewName = mentorController.getSaveMentorPage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_MENTOR_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("mentorDto")).isEqualTo(new MentorDto());
    }

    @Test
    void getSaveMentorPage_withValidId_shouldReturnUpdateMentorPage() {
        given(mentorService.findById(anyInt())).willReturn(mentorDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("mentorDto")).willReturn(mentorDto);
        String viewName = mentorController.getSaveMentorPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_MENTOR_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("mentorDto")).isEqualTo(mentorDto);
    }

    @Test
    void getSaveMentorPage_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> mentorController.getSaveMentorPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveMentor() {
        String viewName = mentorController.save(mentorDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        verify(mentorService).save(mentorDto);
    }

    @Test
    void save_withValidId_shouldUpdateMentorWithGivenId() {
        String viewName = mentorController.save(mentorDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        verify(mentorService).updateById(mentorDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        given(mentorService.updateById(mentorDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> mentorController.save(mentorDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() {
        String viewName = mentorController.deleteById(VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_MENTORS_VIEW);
        verify(mentorService).deleteById(VALID_ID);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        String message = String.format(MENTOR_NOT_FOUND, INVALID_ID);
        doThrow(new ResourceNotFoundException(message)).when(mentorService).deleteById(INVALID_ID);
        assertThatThrownBy(() -> mentorController.deleteById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
