package com.project.ems.integration.feedback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.feedback.FeedbackDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.EndpointConstants.API_FEEDBACKS;
import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedback2;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacks;
import static org.assertj.core.api.Assertions.assertThat;

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
}
