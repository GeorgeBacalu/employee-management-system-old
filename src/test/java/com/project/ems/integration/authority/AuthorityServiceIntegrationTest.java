package com.project.ems.integration.authority;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityRepository;
import com.project.ems.authority.AuthorityServiceImpl;
import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.project.ems.constants.ExceptionMessageConstants.AUTHORITY_NOT_FOUND;
import static com.project.ems.constants.IdentifierConstants.INVALID_ID;
import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthority1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthority2;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AuthorityServiceIntegrationTest {

    @Autowired
    private AuthorityServiceImpl authorityService;

    @MockBean
    private AuthorityRepository authorityRepository;

    @Captor
    private ArgumentCaptor<Authority>authorityCaptor;

    private Authority authority1;
    private Authority authority2;
    private List<Authority> authorities;
    private AuthorityDto authorityDto1;
    private AuthorityDto authorityDto2;
    private List<AuthorityDto> authorityDtos;

    @BeforeEach
    void setUp() {
        authority1 = getMockedAuthority1();
        authority2 = getMockedAuthority2();
        authorities = getMockedAuthorities();
        authorityDto1 = getMockedAuthorityDto1();
        authorityDto2 = getMockedAuthorityDto2();
        authorityDtos = authorityService.convertToDtos(authorities);
    }

    @Test
    void findAll_shouldReturnListOfAuthorities() {
        given(authorityRepository.findAll()).willReturn(authorities);
        List<AuthorityDto> result = authorityService.findAll();
        assertThat(result).isEqualTo(authorityDtos);
    }

    @Test
    void findById_withValidId_shouldReturnAuthorityWithGivenId() {
        given(authorityRepository.findById(anyInt())).willReturn(Optional.ofNullable(authority1));
        AuthorityDto result = authorityService.findById(VALID_ID);
        assertThat(result).isEqualTo(authorityDto1);
    }

    @Test
    void findById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> authorityService.findById(INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(AUTHORITY_NOT_FOUND, INVALID_ID));
    }

    @Test
    void save_shouldAddAuthorityToList() {
        given(authorityRepository.save(any(Authority.class))).willReturn(authority1);
        AuthorityDto result = authorityService.save(authorityDto1);
        verify(authorityRepository).save(authorityCaptor.capture());
        assertThat(result).isEqualTo(authorityService.convertToDto(authorityCaptor.getValue()));
    }

    @Test
    void updateById_withValidId_shouldUpdateAuthorityWithGivenId() {
        Authority authority = authority2; authority.setId(VALID_ID);
        given(authorityRepository.findById(anyInt())).willReturn(Optional.ofNullable(authority1));
        given(authorityRepository.save(any(Authority.class))).willReturn(authority);
        AuthorityDto result = authorityService.updateById(authorityDto2, VALID_ID);
        verify(authorityRepository).save(authorityCaptor.capture());
        assertThat(result).isEqualTo(authorityService.convertToDto(authorityCaptor.getValue()));
    }

    @Test
    void updateById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> authorityService.updateById(authorityDto2, INVALID_ID))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessage(String.format(AUTHORITY_NOT_FOUND, INVALID_ID));
        verify(authorityRepository, never()).save(any(Authority.class));
    }
}
