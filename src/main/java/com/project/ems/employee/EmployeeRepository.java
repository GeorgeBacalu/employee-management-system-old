package com.project.ems.employee;

import com.project.ems.experience.Experience;
import com.project.ems.mentor.Mentor;
import com.project.ems.study.Study;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByMentor(Mentor mentor);

    List<Employee> findAllByExperiencesContains(Experience experience);

    List<Employee> findAllByStudiesContains(Study study);
}
