package com.project.ems.mock;

import com.project.ems.role.Role;
import com.project.ems.role.enums.Authority;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleMock {

    public static List<Role> getMockedRoles() {
        return List.of(getMockedRole1(), getMockedRole2());
    }

    public static Role getMockedRole1() {
        return Role.builder().id(1).authority(Authority.USER).build();
    }

    public static Role getMockedRole2() {
        return Role.builder().id(2).authority(Authority.ADMIN).build();
    }
}
