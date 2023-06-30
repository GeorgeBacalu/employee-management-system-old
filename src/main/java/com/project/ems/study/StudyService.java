package com.project.ems.study;

import java.util.List;

public interface StudyService {

    List<StudyDto> findAll();

    StudyDto findById(Integer id);

    StudyDto save(StudyDto studyDto);

    StudyDto updateById(StudyDto studyDto, Integer id);

    void deleteById(Integer id);

    Study findEntityById(Integer id);
}
