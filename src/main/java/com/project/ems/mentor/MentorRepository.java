package com.project.ems.mentor;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {

    List<Mentor> findAllBySupervisingMentor(Mentor mentor);

    Optional<Mentor> findByExperiencesContains(Experience experience);

    Optional<Mentor> findByStudiesContains(Study study);
}
