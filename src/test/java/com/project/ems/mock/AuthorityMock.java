package com.project.ems.mock;

import com.project.ems.authority.Authority;
import com.project.ems.authority.AuthorityDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.authority.enums.AuthorityType.CREATE;
import static com.project.ems.authority.enums.AuthorityType.DELETE;
import static com.project.ems.authority.enums.AuthorityType.READ;
import static com.project.ems.authority.enums.AuthorityType.UPDATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorityMock {

    public static List<Authority> getMockedAuthorities() {
        return List.of(getMockedAuthority1(), getMockedAuthority2(), getMockedAuthority3(), getMockedAuthority4());
    }

    public static List<Integer> getMockedAuthoritiesIds() {
        return List.of(1, 2, 3, 4);
    }

    public static Authority getMockedAuthority1() {
        return Authority.builder().id(1).type(CREATE).build();
    }

    public static Authority getMockedAuthority2() {
        return Authority.builder().id(2).type(READ).build();
    }

    public static Authority getMockedAuthority3() {
        return Authority.builder().id(3).type(UPDATE).build();
    }

    public static Authority getMockedAuthority4() {
        return Authority.builder().id(4).type(DELETE).build();
    }

    public static AuthorityDto getMockedAuthorityDto1() {
        return AuthorityDto.builder().id(1).type(CREATE).build();
    }

    public static AuthorityDto getMockedAuthorityDto2() {
        return AuthorityDto.builder().id(2).type(READ).build();
    }
}
