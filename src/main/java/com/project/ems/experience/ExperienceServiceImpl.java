package com.project.ems.experience;

import com.project.ems.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExperienceDto> findAll() {
        List<Experience> experiences = experienceRepository.findAll();
        return experiences.stream().map(experience -> convertToDto(modelMapper, experience)).toList();
    }

    @Override
    public ExperienceDto findById(Integer id) {
        Experience experience = findEntityById(id);
        return convertToDto(modelMapper, experience);
    }

    @Override
    public ExperienceDto save(ExperienceDto experienceDto) {
        Experience experience = convertToEntity(modelMapper, experienceDto);
        Experience savedExperience = experienceRepository.save(experience);
        return convertToDto(modelMapper, savedExperience);
    }

    @Override
    public ExperienceDto updateById(ExperienceDto experienceDto, Integer id) {
        Experience experienceToUpdate = findEntityById(id);
        updateEntityFromDto(experienceDto, experienceToUpdate);
        Experience updatedExperience = experienceRepository.save(experienceToUpdate);
        return convertToDto(modelMapper, updatedExperience);
    }

    @Override
    public void deleteById(Integer id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public Experience findEntityById(Integer id) {
        return experienceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Experience with id %s not found", id)));
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
