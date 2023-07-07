package com.project.ems.integration.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.user.UserDto;
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

import static com.project.ems.constants.EndpointConstants.API_PAGINATION_V2;
import static com.project.ems.constants.EndpointConstants.API_USERS;
import static com.project.ems.constants.ExceptionMessageConstants.RESOURCE_NOT_FOUND;
import static com.project.ems.constants.ExceptionMessageConstants.USER_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.mapper.UserMapper.convertToDto;
import static com.project.ems.mapper.UserMapper.convertToDtoList;
import static com.project.ems.mock.UserMock.getMockedUser1;
import static com.project.ems.mock.UserMock.getMockedUser2;
import static com.project.ems.mock.UserMock.getMockedUsers;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static com.project.ems.mock.UserMock.getMockedUsersPage2;
import static com.project.ems.mock.UserMock.getMockedUsersPage3;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRestControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private UserDto userDto1;
    private UserDto userDto2;
    private List<UserDto> userDtos;

    @BeforeEach
    void setUp() {
        userDto1 = convertToDto(modelMapper, getMockedUser1());
        userDto2 = convertToDto(modelMapper, getMockedUser2());
        userDtos = convertToDtoList(modelMapper, getMockedUsers());
    }

    @Test
    void findAll_shouldReturnListOfUsers() throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_USERS, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(userDtos);
    }

    @Test
    void findById_withValidId_shouldReturnUserWithGivenId() {
        ResponseEntity<UserDto> response = template.getForEntity(API_USERS + "/" + VALID_ID, UserDto.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.getForEntity(API_USERS + "/" + INVALID_ID, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(USER_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void save_shouldAddUserToList() throws Exception {
        ResponseEntity<UserDto> saveResponse = template.postForEntity(API_USERS, userDto1, UserDto.class);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(saveResponse.getBody()).isEqualTo(userDto1);

        ResponseEntity<String> getAllResponse = template.getForEntity(API_USERS, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        assertThat(result).isEqualTo(userDtos);
    }

    @Test
    void updateById_withValidId_shouldUpdateUserWithGivenId() {
        UserDto userDto = userDto2; userDto.setId(VALID_ID);
        ResponseEntity<UserDto> updateResponse = template.exchange(API_USERS + "/" + VALID_ID, HttpMethod.PUT, new HttpEntity<>(userDto2), UserDto.class);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isEqualTo(userDto);

        ResponseEntity<UserDto> getResponse = template.getForEntity(API_USERS + "/" + VALID_ID, UserDto.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(userDto);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_USERS + "/" + INVALID_ID, HttpMethod.PUT, new HttpEntity<>(userDto2), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(USER_NOT_FOUND, INVALID_ID)));
    }

    @Test
    void deleteById_withValidId_shouldRemoveUserWithGivenIdFromList() throws Exception {
        ResponseEntity<UserDto> deleteResponse = template.exchange(API_USERS + "/" + VALID_ID, HttpMethod.DELETE, null, UserDto.class);
        assertThat(deleteResponse).isNotNull();
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = template.getForEntity(API_USERS + "/" + VALID_ID, String.class);
        assertThat(getResponse).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(USER_NOT_FOUND, VALID_ID)));

        ResponseEntity<String> getAllResponse = template.getForEntity(API_USERS, String.class);
        assertThat(getAllResponse).isNotNull();
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserDto> result = objectMapper.readValue(getAllResponse.getBody(), new TypeReference<>() {});
        List<UserDto> userDtosCopy = new ArrayList<>(userDtos);
        userDtosCopy.remove(userDto1);
        assertThat(result).isEqualTo(userDtosCopy);
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        ResponseEntity<String> response = template.exchange(API_USERS + "/" + INVALID_ID, HttpMethod.DELETE, null, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(String.format(RESOURCE_NOT_FOUND, String.format(USER_NOT_FOUND, INVALID_ID)));
    }

    private Stream<Arguments> paginationArguments() {
        List<UserDto> userDtosPage1 = convertToDtoList(modelMapper, getMockedUsersPage1());
        List<UserDto> userDtosPage2 = convertToDtoList(modelMapper, getMockedUsersPage2());
        List<UserDto> userDtosPage3 = convertToDtoList(modelMapper, getMockedUsersPage3());
        return Stream.of(Arguments.of(0, 2, "id", "asc", USER_FILTER_KEY, new PageImpl<>(userDtosPage1)),
                         Arguments.of(1, 2, "id", "asc", USER_FILTER_KEY, new PageImpl<>(userDtosPage2)),
                         Arguments.of(2, 2, "id", "asc", USER_FILTER_KEY, new PageImpl<>(Collections.emptyList())),
                         Arguments.of(0, 2, "id", "asc", "", new PageImpl<>(userDtosPage1)),
                         Arguments.of(1, 2, "id", "asc", "", new PageImpl<>(userDtosPage2)),
                         Arguments.of(2, 2, "id", "asc", "", new PageImpl<>(userDtosPage3)));
    }

    @ParameterizedTest
    @MethodSource("paginationArguments")
    void testFindAllByKey(int page, int size, String sortField, String sortDirection, String key, PageImpl<UserDto> expectedPage) throws Exception {
        ResponseEntity<String> response = template.getForEntity(API_USERS + String.format(API_PAGINATION_V2, page, size, sortField, sortDirection, key), String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PageWrapper<UserDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
