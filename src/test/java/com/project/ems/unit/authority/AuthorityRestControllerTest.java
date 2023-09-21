package com.project.ems.unit.authority;

import com.project.ems.authority.AuthorityDto;
import com.project.ems.authority.AuthorityRestController;
import com.project.ems.authority.AuthorityService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.ems.constants.IdentifierConstants.VALID_ID;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorityDto2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthorityRestControllerTest {

    @InjectMocks
    private AuthorityRestController authorityRestController;

    @Mock
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
    void findAll_shouldReturnListOfAuthorities() {
        given(authorityService.findAll()).willReturn(authorityDtos);
        ResponseEntity<List<AuthorityDto>> response = authorityRestController.findAll();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(authorityDtos);
    }

    @Test
    void findById_shouldReturnAuthorityWithGivenId() {
        given(authorityService.findById(anyInt())).willReturn(authorityDto1);
        ResponseEntity<AuthorityDto> response = authorityRestController.findById(VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(authorityDto1);
    }

    @Test
    void save_shouldAddAuthorityToList() {
        given(authorityService.save(any(AuthorityDto.class))).willReturn(authorityDto1);
        ResponseEntity<AuthorityDto> response = authorityRestController.save(authorityDto1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(authorityDto1);
    }

    @Test
    void updateById_shouldUpdateAuthorityWithGivenId() {
        AuthorityDto authorityDto = authorityDto2; authorityDto.setId(VALID_ID);
        given(authorityService.updateById(any(AuthorityDto.class), anyInt())).willReturn(authorityDto);
        ResponseEntity<AuthorityDto> response = authorityRestController.updateById(authorityDto2, VALID_ID);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(authorityDto);
    }
}
