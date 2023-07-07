package com.project.ems.study;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.project.ems.constants.PaginationConstants.STUDY_FILTER_QUERY;

public interface StudyRepository extends JpaRepository<Study, Integer> {

    @Query(STUDY_FILTER_QUERY)
    Page<Study> findAllByKey(Pageable pageable, @Param("key") String key);
}
