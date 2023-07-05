package com.project.ems.study;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyRepository extends JpaRepository<Study, Integer> {

    @Query("select s from Study s where lower(concat(s.title, ' ', s.institution, ' ', s.description, ' ', s.type, ' ', s.startedAt, ' ', s.finishedAt)) like %:key%")
    Page<Study> findAllByKey(Pageable pageable, @Param("key") String key);
}
