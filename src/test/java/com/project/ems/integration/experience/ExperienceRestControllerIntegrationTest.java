package com.project.ems.integration.experience;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.experience.ExperienceDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.EndpointConstants.API_EXPERIENCES;
import static com.project.ems.constants.EndpointConstants.API_PAGINATION_V2;
import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mock.ExperienceMock.getMockedExperience1;
import static com.project.ems.mock.ExperienceMock.getMockedExperience2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage3;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExperienceRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;
    private List<ExperienceDto> experienceDtos;

    @BeforeEach
    void setUp() {
        experienceDto1 = convertToDto(modelMapper, getMockedExperience1());
        experienceDto2 = convertToDto(modelMapper, getMockedExperience2());
        experienceDtos = convertToDtoList(modelMapper, getMockedExperiences());
    }

    @Test
    void findAll_shouldReturnListOfExperiences() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EXPERIENCES, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExperienceDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(experienceDtos);
    }

    @Test
    void findById_withValidId_shouldReturnExperienceWithGivenId() {
        ResponseEntity<ExperienceDto> response = template.getForEntity(API_EXPERIENCES + "/" + VALID_ID, ExperienceDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(experienceDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_EXPERIENCES + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddExperienceToList() throws Exception {
        ResponseEntity<ExperienceDto> saveResponse = template.postForEntity(API_EXPERIENCES, experienceDto1, ExperienceDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveResponse.getBody()).isEqualTo(experienceDto1);

        ResponseEntity<String> getAllResponse = template.getForEntity(API_EXPERIENCES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExperienceDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(experienceDtos);
    }

    @Test
    void updateById_withValidId_shouldUpdateExperienceWithGivenId() {
        ExperienceDto experienceDto = experienceDto2; experienceDto.setId(VALID_ID);
        ResponseEntity<ExperienceDto> updateResponse = template.exchange(API_EXPERIENCES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(experienceDto2), ExperienceDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo(experienceDto);

        ResponseEntity<ExperienceDto> getResponse = template.getForEntity(API_EXPERIENCES + "/" + VALID_ID, ExperienceDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(experienceDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_EXPERIENCES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(experienceDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void deleteById_withValidId_shouldRemoveExperienceWithGivenFromList() throws Exception {
        ResponseEntity<ExperienceDto> deleteResponse = template.exchange(API_EXPERIENCES + "/" + VALID_ID, HttpMethod.DELETE, null, ExperienceDto.class);
        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = template.getForEntity(API_EXPERIENCES + "/" + VALID_ID, String.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EXPERIENCE_NOT_FOUND, VALID_ID)));

        ResponseEntity<String> getAllResponse = template.getForEntity(API_EXPERIENCES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ExperienceDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        List<ExperienceDto> experienceDtosCopy = new ArrayList<>(experienceDtos);
        experienceDtosCopy.remove(experienceDto1);
        assertThat(result).isEqualTo(experienceDtosCopy);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_EXPERIENCES + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(EXPERIENCE_NOT_FOUND, INVALID_ID)));
    }

    private Stream<Arguments> paginationArguments() {
        List<ExperienceDto> experienceDtosPage1 = convertToDtoList(modelMapper, getMockedExperiencesPage1());
        List<ExperienceDto> experienceDtosPage2 = convertToDtoList(modelMapper, getMockedExperiencesPage2());
        List<ExperienceDto> experienceDtosPage3 = convertToDtoList(modelMapper, getMockedExperiencesPage3());
        return Stream.of(Arguments.of(0, 2, "id", "asc", EXPERIENCE_FILTER_KEY, new PageImpl<>(experienceDtosPage1)),
                         Arguments.of(1, 2, "id", "asc", EXPERIENCE_FILTER_KEY, new PageImpl<>(experienceDtosPage2)),
                         Arguments.of(2, 2, "id", "asc", EXPERIENCE_FILTER_KEY, new PageImpl<>(Collections.emptyList())),
                         Arguments.of(0, 2, "id", "asc", "", new PageImpl<>(experienceDtosPage1)),
                         Arguments.of(1, 2, "id", "asc", "", new PageImpl<>(experienceDtosPage2)),
                         Arguments.of(2, 2, "id", "asc", "", new PageImpl<>(experienceDtosPage3)));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, PageImpl<ExperienceDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_EXPERIENCES + String.format(API_PAGINATION_V2, page, size, sortField, sortDirection, key), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<ExperienceDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
