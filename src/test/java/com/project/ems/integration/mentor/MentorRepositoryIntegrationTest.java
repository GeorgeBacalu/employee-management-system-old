package com.project.ems.integration.mentor;

import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mock.MentorMock.getMockedMentorsPage1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MentorRepositoryIntegrationTest {

    @Autowired
    private MentorRepository mentorRepository;

    private List<Mentor> filteredMentorsPage1;

    @BeforeEach
    void setUp() {
        filteredMentorsPage1 = getMockedMentorsPage1();
    }

    @Test
    void findAllByKey_shouldReturnListOfMentorsFilteredByKeyPage1() {
        assertThat(mentorRepository.findAllByKey(pageable, MENTOR_FILTER_KEY).getContent()).isEqualTo(filteredMentorsPage1);
    }
}
