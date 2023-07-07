package com.project.ems.mentor;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MentorService {

    List<MentorDto> findAll();

    MentorDto findById(Integer id);

    MentorDto save(MentorDto mentorDto);

    MentorDto updateById(MentorDto mentorDto, Integer id);

    void deleteById(Integer id);

    Page<MentorDto> findAllByKey(Pageable pageable, String key);

    Mentor findEntityById(Integer id);
}
