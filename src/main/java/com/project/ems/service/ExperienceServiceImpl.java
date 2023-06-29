package com.project.ems.service;

import com.project.ems.entity.Experience;
import com.project.ems.repository.ExperienceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    public List<Experience> findAll() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience findById(Integer id) {
        return experienceRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Experience with id %s not found", id)));
    }

    @Override
    public Experience save(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateById(Experience experience, Integer id) {
        Experience experienceToUpdate = findById(id);
        experienceToUpdate.setTitle(experience.getTitle());
        experienceToUpdate.setOrganization(experience.getOrganization());
        experienceToUpdate.setDescription(experience.getDescription());
        experienceToUpdate.setType(experience.getType());
        experienceToUpdate.setStartedAt(experience.getStartedAt());
        experienceToUpdate.setFinishedAt(experience.getFinishedAt());
        return experienceRepository.save(experienceToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        experienceRepository.deleteById(id);
    }
}
