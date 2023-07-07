package com.project.ems.mentor;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.project.ems.constants.PaginationConstants.MENTOR_FILTER_QUERY;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {

    List<Mentor> findAllBySupervisingMentor(Mentor mentor);

    List<Mentor> findAllByExperiencesContains(Experience experience);

    List<Mentor> findAllByStudiesContains(Study study);

    @Query(MENTOR_FILTER_QUERY)
    Page<Mentor> findAllByKey(Pageable pageable, @Param("key") String key);
}
