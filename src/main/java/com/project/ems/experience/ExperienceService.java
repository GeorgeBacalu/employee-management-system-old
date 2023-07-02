package com.project.ems.experience;

import java.util.List;

public interface ExperienceService {
    
    List<ExperienceDto> findAll();

    ExperienceDto findById(Integer id);

    ExperienceDto save(ExperienceDto experienceDto);

    ExperienceDto updateById(ExperienceDto experienceDto, Integer id);
    
    void deleteById(Integer id);

    Experience findEntityById(Integer id);
}
