package com.project.ems.unit.authority;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityController;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityService;
import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static com.project.ems.constants.ExceptionMessageConstants.AUTHORITY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.constants.ThymeleafViewConstants.AUTHORITIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.AUTHORITY_DETAILS_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.REDIRECT_AUTHORITIES_VIEW;
import static com.project.ems.constants.ThymeleafViewConstants.SAVE_AUTHORITY_VIEW;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthority1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorityControllerTest {

    @InjectMocks
    private AuthorityController authorityController;

    @Mock
    private AuthorityService authorityService;

    @Spy
    private Model model;

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
    void getAllAuthoritiesPage_shouldReturnAuthoritiesPage() {
        given(authorityService.findAll()).willReturn(authorityDtos);
        given(model.getAttribute(anyString())).willReturn(authorities);
        String viewName = authorityController.getAllAuthoritiesPage(model);
        assertThat(viewName).isEqualTo(AUTHORITIES_VIEW);
        assertThat(model.getAttribute("authorities")).isEqualTo(authorities);
    }

    @Test
    void getAuthorityByIdPage_withValidId_shouldReturnAuthorityDetailsPage() {
        given(authorityService.findById(anyInt())).willReturn(authorityDto);
        given(model.getAttribute(anyString())).willReturn(authority);
        String viewName = authorityController.getAuthorityByIdPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(AUTHORITY_DETAILS_VIEW);
        assertThat(model.getAttribute("authority")).isEqualTo(authority);
    }

    @Test
    void getAuthorityByIdPage_withInvalidId_shouldThrowException() {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> authorityController.getAuthorityByIdPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void getSaveAuthorityPage_withNegativeId_shouldReturnSaveAuthorityPage() {
        given(model.getAttribute("id")).willReturn(-1);
        given(model.getAttribute("authorityDto")).willReturn(new AuthorityDto());
        String viewName = authorityController.getSaveAuthorityPage(model, -1);
        assertThat(viewName).isEqualTo(SAVE_AUTHORITY_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(-1);
        assertThat(model.getAttribute("authorityDto")).isEqualTo(new AuthorityDto());
    }

    @Test
    void getSaveAuthorityPage_withValidId_shouldReturnUpdateAuthorityPage() {
        given(authorityService.findById(anyInt())).willReturn(authorityDto);
        given(model.getAttribute("id")).willReturn(VALID_ID);
        given(model.getAttribute("authorityDto")).willReturn(authorityDto);
        String viewName = authorityController.getSaveAuthorityPage(model, VALID_ID);
        assertThat(viewName).isEqualTo(SAVE_AUTHORITY_VIEW);
        assertThat(model.getAttribute("id")).isEqualTo(VALID_ID);
        assertThat(model.getAttribute("authorityDto")).isEqualTo(authorityDto);
    }

    @Test
    void getSaveAuthorityPage_withInvalidId_shouldThrowException() {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.findById(INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> authorityController.getSaveAuthorityPage(model, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }

    @Test
    void save_withNegativeId_shouldSaveAuthority() {
        String viewName = authorityController.save(authorityDto, -1);
        assertThat(viewName).isEqualTo(REDIRECT_AUTHORITIES_VIEW);
        verify(authorityService).save(authorityDto);
    }

    @Test
    void save_withValidId_shouldUpdateAuthorityWithGivenId() {
        String viewName = authorityController.save(authorityDto, VALID_ID);
        assertThat(viewName).isEqualTo(REDIRECT_AUTHORITIES_VIEW);
        verify(authorityService).updateById(authorityDto, VALID_ID);
    }

    @Test
    void save_withInvalidId_shouldThrowException() {
        String message = String.format(AUTHORITY_NOT_FOUND, INVALID_ID);
        given(authorityService.updateById(authorityDto, INVALID_ID)).willThrow(new ResourceNotFoundException(message));
        assertThatThrownBy(() -> authorityController.save(authorityDto, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(message);
    }
}
