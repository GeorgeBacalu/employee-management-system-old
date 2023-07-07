package com.project.ems.experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.project.ems.constants.PaginationConstants.EXPERIENCE_FILTER_QUERY;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    @Query(EXPERIENCE_FILTER_QUERY)
    Page<Experience> findAllByKey(Pageable pageable, @Param("key") String key);
}
