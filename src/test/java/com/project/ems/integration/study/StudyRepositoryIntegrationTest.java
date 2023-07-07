package com.project.ems.integration.study;

import com.project.ems.study.Study;
import com.project.ems.study.StudyRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_KEY;
import static com.project.ems.constants.PaginationConstants.pageable;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage1;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class StudyRepositoryIntegrationTest {

    @Autowired
    private StudyRepository studyRepository;

    private List<Study> filteredStudiesPage1;

    @BeforeEach
    void setUp() {
        filteredStudiesPage1 = getMockedStudiesPage1();
    }

    @Test
    void findAllByKey_shouldReturnListOfStudiesFilteredByKeyPage1() {
        assertThat(studyRepository.findAllByKey(pageable, STUDY_FILTER_KEY).getContent()).isEqualTo(filteredStudiesPage1);
    }
}
