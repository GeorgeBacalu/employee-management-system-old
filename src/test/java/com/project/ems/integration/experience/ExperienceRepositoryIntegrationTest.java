package com.project.ems.integration.experience;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExperienceRepositoryIntegrationTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    private List<Experience> filteredExperiencesPage1;

    @BeforeEach
    void setUp() {
        filteredExperiencesPage1 = getMockedExperiencesPage1();
    }

    @Test
    void findAllByKey_shouldReturnListOfExperiencesFilteredByKeyPage1() {
        assertThat(experienceRepository.findAllByKey(pageable, EXPERIENCE_FILTER_KEY).getContent()).isEqualTo(filteredExperiencesPage1);
    }
}
