package com.project.ems.mentor;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {

    List<Mentor> findAllBySupervisingMentor(Mentor mentor);

    List<Mentor> findAllByExperiencesContains(Experience experience);

    List<Mentor> findAllByStudiesContains(Study study);
}
