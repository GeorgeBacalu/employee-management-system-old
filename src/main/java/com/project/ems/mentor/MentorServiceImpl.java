package com.project.ems.mentor;

import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final RoleService roleService;
    private final StudyService studyService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @Override
    public List<MentorDto> findAll() {
        List<Mentor> mentors = mentorRepository.findAll();
        return mentors.stream().map(mentor -> convertToDto(modelMapper, mentor)).toList();
    }

    @Override
    public MentorDto findById(Integer id) {
        Mentor mentor = findEntityById(id);
        return convertToDto(modelMapper, mentor);
    }

    @Override
    public MentorDto save(MentorDto mentorDto) {
        Mentor mentor = convertToEntity(modelMapper, mentorDto, this, studyService, experienceService);
        Mentor savedMentor = mentorRepository.save(mentor);
        return convertToDto(modelMapper, savedMentor);
    }

    @Override
    public MentorDto updateById(MentorDto mentorDto, Integer id) {
        Mentor mentorToUpdate = findEntityById(id);
        updateEntityFromDto(mentorDto, mentorToUpdate);
        Mentor updatedMentor = mentorRepository.save(mentorToUpdate);
        return convertToDto(modelMapper, updatedMentor);
    }

    @Override
    public void deleteById(Integer id) {
        mentorRepository.deleteById(id);
    }

    @Override
    public Mentor findEntityById(Integer id) {
        return mentorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Mentor with id %s not found", id)));
    }

    private void updateEntityFromDto(MentorDto mentorDto, Mentor mentor) {
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        mentor.setName(mentorDto.getName());
        mentor.setEmail(mentorDto.getEmail());
        mentor.setPassword(mentorDto.getPassword());
        mentor.setMobile(mentorDto.getMobile());
        mentor.setAddress(mentorDto.getAddress());
        mentor.setBirthday(mentorDto.getBirthday());
        mentor.setRole(roleService.findEntityById(mentorDto.getRoleId()));
        mentor.setEmploymentType(mentorDto.getEmploymentType());
        mentor.setPosition(mentorDto.getPosition());
        mentor.setGrade(mentorDto.getGrade());
        mentor.setSupervisingMentor(supervisingMentorId != null ? findEntityById(supervisingMentorId) : null);
        mentor.setStudies(mentorDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        mentor.setExperiences(mentorDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        mentor.setNrTrainees(mentorDto.getNrTrainees());
        mentor.setMaxTrainees(mentorDto.getMaxTrainees());
        mentor.setIsTrainingOpen(mentorDto.getIsTrainingOpen());
    }
}
