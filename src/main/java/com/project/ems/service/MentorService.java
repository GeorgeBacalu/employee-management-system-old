package com.project.ems.service;

import com.project.ems.entity.Mentor;
import java.util.List;

public interface MentorService {

    List<Mentor> findAll();

    Mentor findById(Integer id);

    Mentor save(Mentor mentor);

    Mentor updateById(Mentor mentor, Integer id);

    void deleteById(Integer id);
}
