package com.project.ems.unit.mentor;

import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorRestController;
import com.project.ems.mentor.MentorService;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
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
import static org.mockito.ArgumentMatchers.eq;
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

    @BeforeEach
    void setUp() {
        mentorDto1 = convertToDto(modelMapper, getMockedMentor1());
        mentorDto2 = convertToDto(modelMapper, getMockedMentor2());
        mentorDtos = convertToDtoList(modelMapper, getMockedMentors());
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

    @ParameterizedTest
    @CsvSource({ "1, ${MENTOR_FILTER_KEY}", "2, ${MENTOR_FILTER_KEY}", "3, ${MENTOR_FILTER_KEY}", "1, ''", "2, ''", "3, ''"  })
    void findAllByKey_shouldReturnListOfMentorsFilteredByKey(int page, String key) {
        Pair<List<MentorDto>, Pageable> pair = getFilteredMentorDtosAndPageable(page, key);
        Page<MentorDto> filteredMentorDtosPage = new PageImpl<>(pair.getLeft());
        given(mentorService.findAllByKey(any(Pageable.class), eq(key))).willReturn(filteredMentorDtosPage);
        ResponseEntity<PageWrapper<MentorDto>> response = mentorRestController.findAllByKey(pair.getRight(), key);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new PageWrapper<>(filteredMentorDtosPage.getContent()));
    }

    private Pair<List<MentorDto>, Pageable> getFilteredMentorDtosAndPageable(int page, String key) {
        return switch(page) {
            case 1 -> Pair.of(convertToDtoList(modelMapper, getMockedMentorsPage1()), pageable);
            case 2 -> Pair.of(convertToDtoList(modelMapper, getMockedMentorsPage2()), pageable2);
            case 3 -> Pair.of(key.equals("") ? Collections.emptyList() : convertToDtoList(modelMapper, getMockedMentorsPage3()), pageable3);
            default -> throw new IllegalArgumentException("Invalid page number: " + page);
        };
    }
}
