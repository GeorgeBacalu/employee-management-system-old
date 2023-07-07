package com.project.ems.unit.mentor;

import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRestController;
import com.project.ems.mentor.MentorService;
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
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.constants.PaginationConstants.pageable2;
import static com.project.ems.constants.PaginationConstants.pageable3;
import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToDtoList;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage1;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage2;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MentorRestControllerTest {

    @InjectMocks
    private MentorRestController mentorRestController;

    @Mock
    private MentorService mentorService;

    @Spy
    private ModelMapper modelMapper;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;
    private List<MentorDto> mentorDtosPage1;
    private List<MentorDto> mentorDtosPage2;
    private List<MentorDto> mentorDtosPage3;

    @BeforeEach
    void setUp() {
        mentorDto1 = convertToDto(modelMapper, getMockedMentor1());
        mentorDto2 = convertToDto(modelMapper, getMockedMentor2());
        mentorDtos = convertToDtoList(modelMapper, getMockedMentors());
        mentorDtosPage1 = convertToDtoList(modelMapper, getMockedMentorsPage1());
        mentorDtosPage2 = convertToDtoList(modelMapper, getMockedMentorsPage2());
        mentorDtosPage3 = convertToDtoList(modelMapper, getMockedMentorsPage3());
    }

    @Test
    void findAll_shouldReturnListOfMentors() {
        given(mentorService.findAll()).willReturn(mentorDtos);
        ResponseEntity<List<MentorDto>> response = mentorRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDtos);
    }

    @Test
    void findById_shouldReturnMentorWithGivenId() {
        given(mentorService.findById(anyInt())).willReturn(mentorDto1);
        ResponseEntity<MentorDto> response = mentorRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void save_shouldAddMentorToList() {
        given(mentorService.save(any(MentorDto.class))).willReturn(mentorDto1);
        ResponseEntity<MentorDto> response = mentorRestController.save(mentorDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void updateById_shouldUpdateMentorWithGivenId() {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        given(mentorService.updateById(any(MentorDto.class), anyInt())).willReturn(mentorDto);
        ResponseEntity<MentorDto> response = mentorRestController.updateById(mentorDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDto);
    }

    @Test
    void deleteById_shouldRemoveMentorWithGivenIdFromList() {
        ResponseEntity<Void> response = mentorRestController.deleteById(VALID_ID);
        verify(mentorService).deleteById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfMentorsFilteredByKeyPage1() {
        PageImpl<MentorDto> filteredMentorDtosPage = new PageImpl<>(mentorDtosPage1);
        given(mentorService.findAllByKey(pageable, MENTOR_FILTER_KEY)).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pageable, MENTOR_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfMentorsFilteredByKeyPage2() {
        PageImpl<MentorDto> filteredMentorDtosPage = new PageImpl<>(mentorDtosPage2);
        given(mentorService.findAllByKey(pageable2, MENTOR_FILTER_KEY)).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pageable2, MENTOR_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withFilterKey_shouldReturnListOfMentorsFilteredByKeyPage3() {
        PageImpl<MentorDto> filteredMentorDtosPage = new PageImpl<>(Collections.emptyList());
        given(mentorService.findAllByKey(pageable3, MENTOR_FILTER_KEY)).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pageable3, MENTOR_FILTER_KEY);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfMentorsPage1() {
        PageImpl<MentorDto> filteredMentorDtosPage = new PageImpl<>(mentorDtosPage1);
        given(mentorService.findAllByKey(pageable, "")).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pageable, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfMentorsPage2() {
        PageImpl<MentorDto> filteredMentorDtosPage = new PageImpl<>(mentorDtosPage2);
        given(mentorService.findAllByKey(pageable2, "")).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pageable2, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }

    @Test
    void findAllByKey_withoutFilterKey_shouldReturnListOfMentorsPage3() {
        PageImpl<MentorDto> filteredMentorDtosPage = new PageImpl<>(mentorDtosPage3);
        given(mentorService.findAllByKey(pageable3, "")).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pageable3, "");
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }
}
