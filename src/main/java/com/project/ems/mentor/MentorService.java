package com.project.ems.mentor;

import java.util.List;

public interface MentorService {

    List<Mentor> findAll();

    Mentor findById(Integer id);

    Mentor save(Mentor mentor);

    Mentor updateById(Mentor mentor, Integer id);

    void deleteById(Integer id);
}
