package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.feedback.FeedbackDto;
import com.project.ems.wrapper.PageWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
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

import static com.project.ems.constants.EndpointConstants.API_FEEDBACKS;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION_V2;
import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage3;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FeedbackRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private FeedbackDto feedbackDto1;
    private FeedbackDto feedbackDto2;
    private List<FeedbackDto> feedbackDtos;

    @BeforeEach
    void setUp() {
        feedbackDto1 = convertToDto(modelMapper, getMockedFeedback1());
        feedbackDto2 = convertToDto(modelMapper, getMockedFeedback2());
        feedbackDtos = convertToDtoList(modelMapper, getMockedFeedbacks());
    }

    @Test
    void findAll_shouldReturnListOfFeedbacks() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_FEEDBACKS, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<FeedbackDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(feedbackDtos);
    }

    @Test
    void findById_withValidId_shouldReturnFeedbackWithGivenId() {
        ResponseEntity<FeedbackDto> response = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, FeedbackDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(feedbackDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_FEEDBACKS + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddFeedbackToList() {
        ResponseEntity<FeedbackDto> saveResponse = template.postForEntity(API_FEEDBACKS, feedbackDto1, FeedbackDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        FeedbackDto saveResult = saveResponse.getBody();
        assertThat(Objects.requireNonNull(saveResult).getId()).isEqualTo(feedbackDto1.getId());
        assertThat(saveResult.getType()).isEqualTo(feedbackDto1.getType());
        assertThat(saveResult.getDescription()).isEqualTo(feedbackDto1.getDescription());
        assertThat(saveResult.getUserId()).isEqualTo(feedbackDto1.getUserId());
        assertThat(saveResult.getSentAt()).isNotNull();
    }

    @Test
    void updateById_withValidId_shouldUpdateFeedbackWithGivenId() {
        FeedbackDto feedbackDto = feedbackDto2; feedbackDto.setId(VALID_ID);
        ResponseEntity<FeedbackDto> updateResponse = template.exchange(API_FEEDBACKS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(feedbackDto2), FeedbackDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        FeedbackDto updateResult = updateResponse.getBody();
        assertThat(Objects.requireNonNull(updateResult).getId()).isEqualTo(feedbackDto.getId());
        assertThat(updateResult.getType()).isEqualTo(feedbackDto.getType());
        assertThat(updateResult.getDescription()).isEqualTo(feedbackDto.getDescription());
        assertThat(updateResult.getUserId()).isEqualTo(feedbackDto.getUserId());
        assertThat(updateResult.getSentAt()).isNotNull();

        ResponseEntity<FeedbackDto> getResponse = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, FeedbackDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        FeedbackDto getResult = getResponse.getBody();
        assertThat(Objects.requireNonNull(getResult).getId()).isEqualTo(feedbackDto.getId());
        assertThat(getResult.getType()).isEqualTo(feedbackDto.getType());
        assertThat(getResult.getDescription()).isEqualTo(feedbackDto.getDescription());
        assertThat(getResult.getUserId()).isEqualTo(feedbackDto.getUserId());
        assertThat(getResult.getSentAt()).isNotNull();
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_FEEDBACKS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(feedbackDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void deleteById_withValidId_shouldRemoveFeedbackWithGivenIdFromList() throws Exception {
        ResponseEntity<FeedbackDto> deleteResponse = template.exchange(API_FEEDBACKS + "/" + VALID_ID, HttpMethod.DELETE, null, FeedbackDto.class);
        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = template.getForEntity(API_FEEDBACKS + "/" + VALID_ID, String.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, VALID_ID)));

        ResponseEntity<String> getAllResponse = template.getForEntity(API_FEEDBACKS, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<FeedbackDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        List<FeedbackDto> feedbackDtosCopy = new ArrayList<>(feedbackDtos);
        feedbackDtosCopy.remove(feedbackDto1);
        assertThat(result).isEqualTo(feedbackDtosCopy);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_FEEDBACKS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(FEEDBACK_NOT_FOUND, INVALID_ID)));
    }

    private Stream<Arguments> paginationArguments() {
        Page<FeedbackDto> feedbackDtosPage1 = new PageImpl<>(convertToDtoList(modelMapper, getMockedFeedbacksPage1()));
        Page<FeedbackDto> feedbackDtosPage2 = new PageImpl<>(convertToDtoList(modelMapper, getMockedFeedbacksPage2()));
        Page<FeedbackDto> feedbackDtosPage3 = new PageImpl<>(convertToDtoList(modelMapper, getMockedFeedbacksPage3()));
        Page<EmployeeDto> emptyPage = new PageImpl<>(Collections.emptyList());
        return Stream.of(Arguments.of(0, 2, "id", "asc", FEEDBACK_FILTER_KEY, feedbackDtosPage1),
                         Arguments.of(1, 2, "id", "asc", FEEDBACK_FILTER_KEY, feedbackDtosPage2),
                         Arguments.of(2, 2, "id", "asc", FEEDBACK_FILTER_KEY, emptyPage),
                         Arguments.of(0, 2, "id", "asc", "", feedbackDtosPage1),
                         Arguments.of(1, 2, "id", "asc", "", feedbackDtosPage2),
                         Arguments.of(2, 2, "id", "asc", "", feedbackDtosPage3));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, Page<FeedbackDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_FEEDBACKS + String.format(API_PAGINATION_V2, page, size, sortField, sortDirection, key), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<FeedbackDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
