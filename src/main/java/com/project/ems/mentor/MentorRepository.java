package com.project.ems.mentor;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {

    List<Mentor> findAllBySupervisingMentor(Mentor mentor);

    List<Mentor> findAllByExperiencesContains(Experience experience);

    List<Mentor> findAllByStudiesContains(Study study);

    @Query("select m from Mentor m " +
          "left join m.studies s " +
          "left join m.experiences e " +
          "where lower(concat(m.name, ' ', m.email, ' ', m.mobile, ' ', m.address, ' ', m.birthday, ' ', m.role.authority, ' ', m.employmentType, ' ', m.position, ' ', m.grade, ' ', m.supervisingMentor.name, ' ', m.nrTrainees, ' ', m.maxTrainees, ' ', m.isTrainingOpen)) like %:key% " +
          "or lower(s.title) like %:key% " +
          "or lower(e.title) like %:key%")
    Page<Mentor> findAllByKey(Pageable pageable, @Param("key") String key);
}
