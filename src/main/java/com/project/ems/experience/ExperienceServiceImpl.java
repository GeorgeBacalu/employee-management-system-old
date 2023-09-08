package com.project.ems.experience;

import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.MentorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final EmployeeRepository employeeRepository;
    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExperienceDto> findAll() {
        List<Experience> experiences = experienceRepository.findAll();
        return !experiences.isEmpty() ? convertToDtos(experiences) : new ArrayList<>();
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
        Experience experienceToDelete = findEntityById(id);
        employeeRepository.findAllByExperiencesContains(experienceToDelete).forEach(employee -> employee.getExperiences().remove(experienceToDelete));
        mentorRepository.findAllByExperiencesContains(experienceToDelete).forEach(mentor -> mentor.getExperiences().remove(experienceToDelete));
        experienceRepository.delete(experienceToDelete);
    }

    @Override
    public Page<ExperienceDto> findAllByKey(Pageable pageable, String key) {
        Page<Experience> experiencesPage = key.trim().equals("") ? experienceRepository.findAll(pageable) : experienceRepository.findAllByKey(pageable, key.toLowerCase());
        return experiencesPage.hasContent() ? experiencesPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public List<ExperienceDto> convertToDtos(List<Experience> experiences) {
        return experiences.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Experience> convertToEntities(List<ExperienceDto> experienceDtos) {
        return experienceDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public ExperienceDto convertToDto(Experience experience) {
        return modelMapper.map(experience, ExperienceDto.class);
    }

    @Override
    public Experience convertToEntity(ExperienceDto experienceDto) {
        return modelMapper.map(experienceDto, Experience.class);
    }

    @Override
    public Experience findEntityById(Integer id) {
        return experienceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(EXPERIENCE_NOT_FOUND, id)));
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
