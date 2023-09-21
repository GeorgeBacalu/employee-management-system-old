package com.project.ems.integration.authority;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityRestController;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.ems.constants.EndpointConstants.API_AUTHORITIES;
import static com.project.ems.constants.ExceptionMessageConstants.AUTHORITY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorityRestController.class)
@ExtendWith(MockitoExtension.class)
class AuthorityRestControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
        given(authorityService.findAll()).willReturn(authorityDtos);
        ResultActions actions = mockMvc.perform(get(API_AUTHORITIES)).andExpect(status().isOk());
        for(int i = 0; i < authorityDtos.size(); i++) {
            assertAuthorityDto(actions, "$[" + i + "]", authorityDtos.get(i));
        }
        List<AuthorityDto> response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(response).isEqualTo(authorityDtos);
    }

    @Test
    void findById_withValidId_shouldReturnAuthorityWithGivenId() throws Exception {
        given(authorityService.findById(anyInt())).willReturn(authorityDto1);
        ResultActions actions = mockMvc.perform(get(API_AUTHORITIES + "/{id}", VALID_ID)).andExpect(status().isOk());
        assertAuthorityDtoJson(actions, authorityDto1);
        AuthorityDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AuthorityDto.class);
        assertThat(response).isEqualTo(authorityDto1);
        verify(authorityService).findById(VALID_ID);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(API_AUTHORITIES + "/{id}", INVALID_ID))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(authorityService).findById(INVALID_ID);
    }

    @Test
    void save_shouldAddAuthorityToList() throws Exception {
        given(authorityService.save(any(AuthorityDto.class))).willReturn(authorityDto1);
        ResultActions actions = mockMvc.perform(post(API_AUTHORITIES)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(authorityDto1)))
              .andExpect(status().isCreated());
        assertAuthorityDtoJson(actions, authorityDto1);
        AuthorityDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AuthorityDto.class);
        assertThat(response).isEqualTo(authorityDto1);
        verify(authorityService).save(authorityDto1);
    }

    @Test
    void updateById_withValidId_shouldUpdateAuthorityWithGivenId() throws Exception {
        AuthorityDto authorityDto = authorityDto2; authorityDto.setId(VALID_ID);
        given(authorityService.updateById(any(AuthorityDto.class), anyInt())).willReturn(authorityDto);
        ResultActions actions = mockMvc.perform(put(API_AUTHORITIES + "/{id}", VALID_ID)
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(authorityDto2)))
              .andExpect(status().isOk());
        assertAuthorityDtoJson(actions, authorityDto);
        AuthorityDto response = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AuthorityDto.class);
        assertThat(response).isEqualTo(authorityDto);
        verify(authorityService).updateById(authorityDto2, VALID_ID);
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.updateById(any(AuthorityDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(put(API_AUTHORITIES + "/{id}", INVALID_ID)
              .contentType(APPLICATION_JSON_VALUE)
              .content(objectMapper.writeValueAsString(authorityDto2)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(authorityService).updateById(authorityDto2, INVALID_ID);
    }

    private void assertAuthorityDto(ResultActions actions, String prefix, AuthorityDto authorityDto) throws Exception {
        actions.andExpect(jsonPath(prefix + ".id").value(authorityDto.getId()))
              .andExpect(jsonPath(prefix + ".type").value(authorityDto.getType().name()));
    }

    private void assertAuthorityDtoJson(ResultActions actions, AuthorityDto authorityDto) throws Exception {
        actions.andExpect(jsonPath("$.id").value(authorityDto.getId()))
              .andExpect(jsonPath("$.type").value(authorityDto.getType().name()));
    }
}
