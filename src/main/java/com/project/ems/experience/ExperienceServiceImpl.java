package com.project.ems.experience;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    public List<ExperienceDto> findAll() {
        List<Experience> experiences = experienceRepository.findAll();
        return experiences.stream().map(this::convertToDto).toList();
    }

    @Override
    public ExperienceDto findById(Integer id) {
        Experience experience = findEntityById(id);
        return convertToDto(experience);
    }

    @Override
    public ExperienceDto save(ExperienceDto experienceDto) {
        Experience experience = convertToEntity(experienceDto);
        Experience savedExperience = experienceRepository.save(experience);
        return convertToDto(savedExperience);
    }

    @Override
    public ExperienceDto updateById(ExperienceDto experienceDto, Integer id) {
        Experience experienceToUpdate = findEntityById(id);
        updateEntityFromDto(experienceDto, experienceToUpdate);
        Experience updatedExperience = experienceRepository.save(experienceToUpdate);
        return convertToDto(updatedExperience);
    }

    @Override
    public void deleteById(Integer id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public Experience findEntityById(Integer id) {
        return experienceRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Experience with id %s not found", id)));
    }

    private ExperienceDto convertToDto(Experience experience) {
        return ExperienceDto.builder()
              .id(experience.getId())
              .title(experience.getTitle())
              .organization(experience.getOrganization())
              .description(experience.getDescription())
              .type(experience.getType())
              .startedAt(experience.getStartedAt())
              .finishedAt(experience.getFinishedAt())
              .build();
    }

    private Experience convertToEntity(ExperienceDto experienceDto) {
        return Experience.builder()
              .id(experienceDto.getId())
              .title(experienceDto.getTitle())
              .organization(experienceDto.getOrganization())
              .description(experienceDto.getDescription())
              .type(experienceDto.getType())
              .startedAt(experienceDto.getStartedAt())
              .finishedAt(experienceDto.getFinishedAt())
              .build();
    }

    private void updateEntityFromDto(ExperienceDto experienceDto, Experience experience) {
        experience.setTitle(experienceDto.getTitle());
        experience.setOrganization(experienceDto.getOrganization());
        experience.setDescription(experienceDto.getDescription());
        experience.setType(experienceDto.getType());
        experience.setStartedAt(experienceDto.getStartedAt());
        experience.setFinishedAt(experienceDto.getFinishedAt());
    }
}
