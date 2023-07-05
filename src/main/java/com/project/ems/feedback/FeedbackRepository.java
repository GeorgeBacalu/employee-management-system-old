package com.project.ems.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("select f from Feedback f where lower(concat(f.type, ' ', f.description, ' ', f.sentAt, ' ', f.user.name)) like %:key%")
    Page<Feedback> findAllByKey(Pageable pageable, @Param("key") String key);
}
