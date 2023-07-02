package com.project.ems.employee;

import com.project.ems.experience.Experience;
import com.project.ems.mentor.Mentor;
import com.project.ems.role.Role;
import com.project.ems.study.Study;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByMentor(Mentor mentor);

    Optional<Employee> findByExperiencesContains(Experience experience);

    Optional<Employee> findByStudiesContains(Study study);

    void deleteAllByRole(Role role);
}
