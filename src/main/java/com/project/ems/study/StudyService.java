package com.project.ems.study;

import java.util.List;

public interface StudyService {

    List<Study> findAll();

    Study findById(Integer id);

    Study save(Study study);

    Study updateById(Study study, Integer id);

    void deleteById(Integer id);
}
