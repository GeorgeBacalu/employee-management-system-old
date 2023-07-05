package com.project.ems.employee;

import com.project.ems.experience.Experience;
import com.project.ems.mentor.Mentor;
import com.project.ems.study.Study;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByMentor(Mentor mentor);

    List<Employee> findAllByExperiencesContains(Experience experience);

    List<Employee> findAllByStudiesContains(Study study);

    @Query("select e from Employee e " +
           "left join e.studies s " +
           "left join e.experiences ex " +
           "where lower(concat(e.name, ' ', e.email, ' ', e.mobile, ' ', e.address, ' ', e.birthday, ' ', e.role.authority, ' ', e.employmentType, ' ', e.position, ' ', e.grade, ' ', e.mentor.name)) like %:key% " +
           "or lower(s.title) like %:key% " +
           "or lower(ex.title) like %:key%")
    Page<Employee> findAllByKey(Pageable pageable, @Param("key") String key);

}
