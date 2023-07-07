package com.project.ems.study;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyService {

    List<StudyDto> findAll();

    StudyDto findById(Integer id);

    StudyDto save(StudyDto studyDto);

    StudyDto updateById(StudyDto studyDto, Integer id);

    void deleteById(Integer id);

    Page<StudyDto> findAllByKey(Pageable pageable, String key);

    Study findEntityById(Integer id);
}
