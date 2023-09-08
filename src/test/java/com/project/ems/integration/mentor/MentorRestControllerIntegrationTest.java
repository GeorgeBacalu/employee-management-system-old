package com.project.ems.integration.mentor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
import com.project.ems.wrapper.PageWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.EndpointConstants.API_MENTORS;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION_V2;
import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.mock.MentorMock.getMockedMentorDto1;
import static com.project.ems.mock.MentorMock.getMockedMentorDto2;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosPage1;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosPage2;
import static com.project.ems.mock.MentorMock.getMockedMentorDtosPage3;
import static com.project.ems.mock.MentorMock.getMockedMentors;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MentorRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MentorService mentorService;

    private MentorDto mentorDto1;
    private MentorDto mentorDto2;
    private List<MentorDto> mentorDtos;

    @BeforeEach
    void setUp() {
        mentorDto1 = getMockedMentorDto1();
        mentorDto2 = getMockedMentorDto2();
        mentorDtos = mentorService.convertToDtos(getMockedMentors());
    }

    @Test
    void findAll_shouldReturnListOfMentors() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_MENTORS, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<MentorDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(mentorDtos);
    }

    @Test
    void findById_withValidId_shouldReturnMentorWithGivenId() {
        ResponseEntity<MentorDto> response = template.getForEntity(API_MENTORS + "/" + VALID_ID, MentorDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mentorDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_MENTORS + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddMentorToList() throws Exception {
        ResponseEntity<MentorDto> saveResponse = template.postForEntity(API_MENTORS, mentorDto1, MentorDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveResponse.getBody()).isEqualTo(mentorDto1);

        ResponseEntity<String> getAllResponse = template.getForEntity(API_MENTORS, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<MentorDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(mentorDtos);
    }

    @Test
    void updateById_withValidId_shouldUpdateMentorWithGivenId() {
        MentorDto mentorDto = mentorDto2; mentorDto.setId(VALID_ID);
        ResponseEntity<MentorDto> updateResponse = template.exchange(API_MENTORS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(mentorDto2), MentorDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo(mentorDto);

        ResponseEntity<MentorDto> getResponse = template.getForEntity(API_MENTORS + "/" + VALID_ID, MentorDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(mentorDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_MENTORS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(mentorDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void deleteById_withValidId_shouldRemoveMentorWithGivenIdFromList() throws Exception {
        ResponseEntity<MentorDto> deleteResponse = template.exchange(API_MENTORS + "/" + VALID_ID, HttpMethod.DELETE, null, MentorDto.class);
        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = template.getForEntity(API_MENTORS + "/" + VALID_ID, String.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, VALID_ID)));

        ResponseEntity<String> getAllResponse = template.getForEntity(API_MENTORS, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<MentorDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        List<MentorDto> mentorDtosCopy = new ArrayList<>(mentorDtos);
        for(int i = 6; i < 12; i++) {
            mentorDtosCopy.get(i).setSupervisingMentorId(null);
        }
        mentorDtosCopy.remove(mentorDto1);
        assertThat(result).isEqualTo(mentorDtosCopy);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_MENTORS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(MENTOR_NOT_FOUND, INVALID_ID)));
    }

    private Stream<Arguments> paginationArguments() {
        Page<MentorDto> mentorDtosPage1 = new PageImpl<>(getMockedMentorDtosPage1());
        Page<MentorDto> mentorDtosPage2 = new PageImpl<>(getMockedMentorDtosPage2());
        Page<MentorDto> mentorDtosPage3 = new PageImpl<>(getMockedMentorDtosPage3());
        Page<MentorDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", MENTOR_FILTER_KEY, mentorDtosPage1),
                         Arguments.of(1, 2, "id", "asc", MENTOR_FILTER_KEY, mentorDtosPage2),
                         Arguments.of(2, 2, "id", "asc", MENTOR_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", mentorDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", mentorDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", mentorDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<MentorDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_MENTORS + String.format(API_PAGINATION_V2, page, size, sortField, sortDirection, key), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<MentorDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
