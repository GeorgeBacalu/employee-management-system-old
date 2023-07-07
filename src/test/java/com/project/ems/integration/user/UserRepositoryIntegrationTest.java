package com.project.ems.integration.user;

import com.project.ems.user.User;
import com.project.ems.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.PaginationConstants.USER_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private List<User> filteredUsersPage1;

    @BeforeEach
    void setUp() {
        filteredUsersPage1 = getMockedUsersPage1();
    }

    @Test
    void findAllByKey_shouldReturnListOfUsersFilteredByKeyPage1() {
        assertThat(userRepository.findAllByKey(pageable, USER_FILTER_KEY).getContent()).isEqualTo(filteredUsersPage1);
    }
}
