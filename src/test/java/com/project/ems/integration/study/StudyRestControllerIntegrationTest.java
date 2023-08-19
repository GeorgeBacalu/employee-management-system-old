package com.project.ems.integration.study;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.study.StudyDto;
import com.project.ems.study.StudyService;
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

import static com.project.ems.constants.EndpointConstants.API_PAGINATION_V2;
import static com.project.ems.constants.EndpointConstants.API_STUDIES;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.mock.StudyMock.getMockedStudies;
import static com.project.ems.mock.StudyMock.getMockedStudyDto1;
import static com.project.ems.mock.StudyMock.getMockedStudyDto2;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage1;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage2;
import static com.project.ems.mock.StudyMock.getMockedStudyDtosPage3;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class StudyRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
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
    void findAll_shouldReturnListOfStudies() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_STUDIES, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<StudyDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(studyDtos);
    }

    @Test
    void findById_withValidId_shouldReturnStudyWithGivenId() {
        ResponseEntity<StudyDto> response = template.getForEntity(API_STUDIES + "/" + VALID_ID, StudyDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(studyDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_STUDIES + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(STUDY_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddStudyToList() throws Exception {
        ResponseEntity<StudyDto> saveResponse = template.postForEntity(API_STUDIES, studyDto1, StudyDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveResponse.getBody()).isEqualTo(studyDto1);

        ResponseEntity<String> getAllResponse = template.getForEntity(API_STUDIES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<StudyDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(studyDtos);
    }

    @Test
    void updateById_withValidId_shouldUpdateStudyWithGivenId() {
        StudyDto studyDto = studyDto2; studyDto.setId(VALID_ID);
        ResponseEntity<StudyDto> updateResponse = template.exchange(API_STUDIES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(studyDto2), StudyDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo(studyDto);

        ResponseEntity<StudyDto> getResponse = template.getForEntity(API_STUDIES + "/" + VALID_ID, StudyDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(studyDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_STUDIES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(studyDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(STUDY_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void deleteById_withValidId_shouldRemoveStudyWithGivenIdFromList() throws Exception {
        ResponseEntity<StudyDto> deleteResponse = template.exchange(API_STUDIES + "/" + VALID_ID, HttpMethod.DELETE, null, StudyDto.class);
        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = template.getForEntity(API_STUDIES + "/" + VALID_ID, String.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(STUDY_NOT_FOUND, VALID_ID)));

        ResponseEntity<String> getAllResponse = template.getForEntity(API_STUDIES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<StudyDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        List<StudyDto> studyDtosCopy = new ArrayList<>(studyDtos);
        studyDtosCopy.remove(studyDto1);
        assertThat(result).isEqualTo(studyDtosCopy);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_STUDIES + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(STUDY_NOT_FOUND, INVALID_ID)));
    }

    private Stream<Arguments> paginationArguments() {
        Page<StudyDto> studyDtosPage1 = new PageImpl<>(getMockedStudyDtosPage1());
        Page<StudyDto> studyDtosPage2 = new PageImpl<>(getMockedStudyDtosPage2());
        Page<StudyDto> studyDtosPage3 = new PageImpl<>(getMockedStudyDtosPage3());
        Page<StudyDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", STUDY_FILTER_KEY, studyDtosPage1),
                         Arguments.of(1, 2, "id", "asc", STUDY_FILTER_KEY, studyDtosPage2),
                         Arguments.of(2, 2, "id", "asc", STUDY_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", studyDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", studyDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", studyDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<StudyDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_STUDIES + String.format(API_PAGINATION_V2, page, size, sortField, sortDirection, key), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<StudyDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
