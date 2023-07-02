package com.project.ems.experience;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.EXPERIENCE_NOT_FOUND;
import static com.project.ems.mapper.ExperienceMapper.convertToDto;
import static com.project.ems.mapper.ExperienceMapper.convertToDtoList;
import static com.project.ems.mapper.ExperienceMapper.convertToEntity;

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
        return convertToDtoList(modelMapper, experiences);
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
        Experience experienceToDelete = findEntityById(id);
        List<Employee> employees = employeeRepository.findAllByExperiencesContains(experienceToDelete);
        employees.forEach(employee -> employee.getExperiences().remove(experienceToDelete));
        List<Mentor> mentors = mentorRepository.findAllByExperiencesContains(experienceToDelete);
        mentors.forEach(mentor -> mentor.getExperiences().remove(experienceToDelete));
        experienceRepository.delete(experienceToDelete);
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
