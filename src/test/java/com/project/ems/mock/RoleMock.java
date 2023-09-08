package com.project.ems.mock;

import com.project.ems.role.Role;
import com.project.ems.role.RoleDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.role.enums.Authority.ADMIN;
import static com.project.ems.role.enums.Authority.USER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleMock {

    public static List<Role> getMockedRoles() {
        return List.of(getMockedRole1(), getMockedRole2());
    }

    public static Role getMockedRole1() { return Role.builder().id(1).authority(USER).build(); }

    public static Role getMockedRole2() { return Role.builder().id(2).authority(ADMIN).build(); }

    public static RoleDto getMockedRoleDto1() { return RoleDto.builder().id(1).authority(USER).build(); }

    public static RoleDto getMockedRoleDto2() { return RoleDto.builder().id(2).authority(ADMIN).build(); }
}
