package com.project.ems.integration.authority;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.EndpointConstants.API_AUTHORITIES;
import static com.project.ems.constants.ExceptionMessageConstants.AUTHORITY_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto2;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthorityRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorityService authorityService;

    private AuthorityDto authorityDto1;
    private AuthorityDto authorityDto2;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authorityDto1 = getMockedAuthorityDto1();
        authorityDto2 = getMockedAuthorityDto2();
        authorityDtos = authorityService.convertToDtos(getMockedAuthorities());
    }

    @Test
    void findAll_shouldReturnListOfAuthorities() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_AUTHORITIES, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<AuthorityDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(authorityDtos);
    }

    @Test
    void findById_withValidId_shouldReturnAuthorityWithGivenId() {
        ResponseEntity<AuthorityDto> response = template.getForEntity(API_AUTHORITIES + "/" + VALID_ID, AuthorityDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(authorityDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_AUTHORITIES + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(AUTHORITY_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddAuthorityToList() throws Exception {
        ResponseEntity<AuthorityDto> saveResponse = template.postForEntity(API_AUTHORITIES, authorityDto1, AuthorityDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveResponse.getBody()).isEqualTo(authorityDto1);

        ResponseEntity<String> getAllResponse = template.getForEntity(API_AUTHORITIES, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<AuthorityDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(authorityDtos);
    }

    @Test
    void updateById_withValidId_shouldUpdateAuthorityWithGivenId() {
        AuthorityDto authorityDto = authorityDto2; authorityDto.setId(VALID_ID);
        ResponseEntity<AuthorityDto> updateResponse = template.exchange(API_AUTHORITIES + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(authorityDto2), AuthorityDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo(authorityDto);

        ResponseEntity<AuthorityDto> getResponse = template.getForEntity(API_AUTHORITIES + "/" + VALID_ID, AuthorityDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(authorityDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_AUTHORITIES + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(authorityDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(AUTHORITY_NOT_FOUND, INVALID_ID)));
    }
}
