package com.project.ems.mock;

import com.project.ems.user.User;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static List<User> getMockedUsers() {
        return List.of(getMockedUser1(), getMockedUser2());
    }

    public static User getMockedUser1() {
        return User.builder()
              .id(1)
              .name("User_name1")
              .email("User_email1@email.com")
              .password("#User_password1")
              .mobile("40700000001")
              .address("User_address1")
              .birthday(LocalDate.of(1990, 1, 1))
              .role(getMockedRole1())
              .build();
    }

    public static User getMockedUser2() {
        return User.builder()
              .id(2)
              .name("User_name2")
              .email("User_email2@email.com")
              .password("#User_password2")
              .mobile("40700000002")
              .address("User_address2")
              .birthday(LocalDate.of(1990, 1, 2))
              .role(getMockedRole2())
              .build();
    }
}
