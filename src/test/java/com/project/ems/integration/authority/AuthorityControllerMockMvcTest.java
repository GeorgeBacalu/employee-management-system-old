package com.project.ems.integration.authority;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.role.RoleController;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.project.ems.constants.EndpointConstants.AUTHORITIES;
import static com.project.ems.constants.ExceptionMessageConstants.AUTHORITY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.AUTHORITIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.AUTHORITY_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_AUTHORITIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_AUTHORITY_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.TEXT_HTML_UTF8;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthority1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RoleController.class)
class AuthorityControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorityService authorityService;

    private Authority authority;
    private List<Authority> authorities;
    private AuthorityDto authorityDto;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authority = getMockedAuthority1();
        authorities = getMockedAuthorities();
        authorityDto = getMockedAuthorityDto1();
        authorityDtos = authorityService.convertToDtos(authorities);
    }

    @Test
    void getAllAuthoritiesPage_shouldReturnAuthoritiesPage() throws Exception {
        given(authorityService.findAll()).willReturn(authorityDtos);
        given(authorityService.convertToEntities(authorityDtos)).willReturn(authorities);
        mockMvc.perform(get(AUTHORITIES).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(AUTHORITIES_VIEW))
              .andExpect(model().attribute("authorities", authorities));
        verify(authorityService).findAll();
    }

    @Test
    void getAuthorityByIdPage_withValidId_shouldReturnAuthorityDetailsPage() throws Exception {
        given(authorityService.findById(anyInt())).willReturn(authorityDto);
        given(authorityService.convertToEntity(authorityDto)).willReturn(authority);
        mockMvc.perform(get(AUTHORITIES + "/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(AUTHORITY_DETAILS_VIEW))
              .andExpect(model().attribute("authority", authority));
        verify(authorityService).findById(VALID_ID);
    }

    @Test
    void getAuthorityByIdPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(AUTHORITIES + "/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(authorityService).findById(INVALID_ID);
    }

    @Test
    void getSaveAuthorityPage_withNegativeId_shouldReturnSaveAuthorityPage() throws Exception {
        mockMvc.perform(get(AUTHORITIES + "/save/{id}", -1).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_AUTHORITY_VIEW))
              .andExpect(model().attribute("id", -1))
              .andExpect(model().attribute("authorityDto", new AuthorityDto()));
    }

    @Test
    void getSaveAuthorityPage_withValidId_shouldReturnUpdateAuthorityPage() throws Exception {
        given(authorityService.findById(anyInt())).willReturn(authorityDto);
        mockMvc.perform(get(AUTHORITIES + "/save/{id}", VALID_ID).accept(TEXT_HTML))
              .andExpect(status().isOk())
              .andExpect(content().contentType(TEXT_HTML_UTF8))
              .andExpect(view().name(SAVE_AUTHORITY_VIEW))
              .andExpect(model().attribute("id", VALID_ID))
              .andExpect(model().attribute("authorityDto", authorityDto));
    }

    @Test
    void getSaveAuthorityPage_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findById(anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(get(AUTHORITIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
    }

    @Test
    void save_withNegativeId_shouldSaveAuthority() throws Exception {
        mockMvc.perform(post(AUTHORITIES + "/save/{id}", -1).accept(TEXT_HTML)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .params(convertToMultiValueMap(authorityDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_AUTHORITIES_VIEW))
              .andExpect(redirectedUrl(AUTHORITIES));
        verify(authorityService).updateById(authorityDto, VALID_ID);
    }

    @Test
    void save_withValidId_shouldUpdateAuthorityWithGivenId() throws Exception {
        mockMvc.perform(post(AUTHORITIES + "/save/{id}", VALID_ID).accept(TEXT_HTML)
              .contentType(APPLICATION_FORM_URLENCODED)
              .params(convertToMultiValueMap(authorityDto)))
              .andExpect(status().isFound())
              .andExpect(view().name(REDIRECT_AUTHORITIES_VIEW))
              .andExpect(redirectedUrl(AUTHORITIES));
        verify(authorityService).updateById(authorityDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() throws Exception {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.updateById(any(AuthorityDto.class), anyInt())).willThrow(new ResourceNotFoundException(message));
        mockMvc.perform(post(AUTHORITIES + "/save/{id}", INVALID_ID).accept(TEXT_HTML)
              .contentType(APPLICATION_FORM_URLENCODED)
              .params(convertToMultiValueMap(authorityDto)))
              .andExpect(status().isNotFound())
              .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
              .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo(message));
        verify(authorityService).updateById(any(AuthorityDto.class), anyInt());
    }

    private MultiValueMap<String, String> convertToMultiValueMap(AuthorityDto authorityDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", authorityDto.getType().toString());
        return params;
    }
}
