package com.project.ems.integration.feedback;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class FeedbackRepositoryIntegrationTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private List<Feedback> filteredFeedbacksPage1;

    @BeforeEach
    void setUp() {
        filteredFeedbacksPage1 = getMockedFeedbacksPage1();
    }

    @Test
    void findAllByKey_shouldReturnListOfFeedbacksFilteredByKeyPage1() {
        assertThat(feedbackRepository.findAllByKey(pageable, FEEDBACK_FILTER_KEY).getContent()).isEqualTo(filteredFeedbacksPage1);
    }
}
