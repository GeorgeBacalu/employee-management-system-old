package com.project.ems.service;

import com.project.ems.entity.Experience;
import java.util.List;

public interface ExperienceService {
    
    List<Experience> findAll();
    
    Experience findById(Integer id);
    
    Experience save(Experience experience);
    
    Experience updateById(Experience experience, Integer id);
    
    void deleteById(Integer id);
}
