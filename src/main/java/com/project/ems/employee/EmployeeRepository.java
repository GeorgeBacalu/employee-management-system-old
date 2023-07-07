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

import static com.project.ems.constants.PaginationConstants.EMPLOYEE_FILTER_QUERY;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByMentor(Mentor mentor);

    List<Employee> findAllByExperiencesContains(Experience experience);

    List<Employee> findAllByStudiesContains(Study study);

    @Query(EMPLOYEE_FILTER_QUERY)
    Page<Employee> findAllByKey(Pageable pageable, @Param("key") String key);

}
