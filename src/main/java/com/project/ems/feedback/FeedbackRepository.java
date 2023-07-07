package com.project.ems.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.project.ems.constants.PaginationConstants.FEEDBACK_FILTER_QUERY;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query(FEEDBACK_FILTER_QUERY)
    Page<Feedback> findAllByKey(Pageable pageable, @Param("key") String key);
}
