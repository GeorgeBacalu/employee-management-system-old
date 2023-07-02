package com.project.ems.mentor;

import java.util.List;

public interface MentorService {

    List<MentorDto> findAll();

    MentorDto findById(Integer id);

    MentorDto save(MentorDto mentorDto);

    MentorDto updateById(MentorDto mentorDto, Integer id);

    void deleteById(Integer id);

    Mentor findEntityById(Integer id);
}
