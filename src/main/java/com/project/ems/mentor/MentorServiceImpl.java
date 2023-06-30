package com.project.ems.mentor;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final RoleService roleService;
    private final StudyService studyService;
    private final ExperienceService experienceService;

    @Override
    public List<MentorDto> findAll() {
        List<Mentor> mentors = mentorRepository.findAll();
        return mentors.stream().map(this::convertToDto).toList();
    }

    @Override
    public MentorDto findById(Integer id) {
        Mentor mentor = findEntityById(id);
        return convertToDto(mentor);
    }

    @Override
    public MentorDto save(MentorDto mentorDto) {
        Mentor mentor = convertToEntity(mentorDto);
        Mentor savedMentor = mentorRepository.save(mentor);
        return convertToDto(savedMentor);
    }

    @Override
    public MentorDto updateById(MentorDto mentorDto, Integer id) {
        Mentor mentorToUpdate = findEntityById(id);
        updateEntityFromDto(mentorDto, mentorToUpdate);
        Mentor updatedMentor = mentorRepository.save(mentorToUpdate);
        return convertToDto(updatedMentor);
    }

    @Override
    public void deleteById(Integer id) {
        mentorRepository.deleteById(id);
    }

    @Override
    public Mentor findEntityById(Integer id) {
        return mentorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Mentor with id %s not found", id)));
    }

    private MentorDto convertToDto(Mentor mentor) {
        Mentor supervisingMentor = mentor.getSupervisingMentor();
        return MentorDto.builder()
              .id(mentor.getId())
              .name(mentor.getName())
              .email(mentor.getEmail())
              .password(mentor.getPassword())
              .mobile(mentor.getMobile())
              .address(mentor.getAddress())
              .birthday(mentor.getBirthday())
              .roleId(mentor.getRole().getId())
              .employmentType(mentor.getEmploymentType())
              .position(mentor.getPosition())
              .grade(mentor.getGrade())
              .supervisingMentorId(supervisingMentor != null ? supervisingMentor.getId() : null)
              .studiesIds(mentor.getStudies().stream().map(Study::getId).toList())
              .experiencesIds(mentor.getExperiences().stream().map(Experience::getId).toList())
              .nrTrainees(mentor.getNrTrainees())
              .maxTrainees(mentor.getMaxTrainees())
              .isTrainingOpen(mentor.getIsTrainingOpen())
              .build();
    }

    private Mentor convertToEntity(MentorDto mentorDto) {
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        return Mentor.builder()
              .id(mentorDto.getId())
              .name(mentorDto.getName())
              .email(mentorDto.getEmail())
              .password(mentorDto.getPassword())
              .mobile(mentorDto.getMobile())
              .address(mentorDto.getAddress())
              .birthday(mentorDto.getBirthday())
              .role(roleService.findEntityById(mentorDto.getRoleId()))
              .employmentType(mentorDto.getEmploymentType())
              .position(mentorDto.getPosition())
              .grade(mentorDto.getGrade())
              .supervisingMentor(supervisingMentorId != null ? findEntityById(supervisingMentorId) : null)
              .studies(mentorDto.getStudiesIds().stream().map(studyService::findEntityById).toList())
              .experiences(mentorDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList())
              .nrTrainees(mentorDto.getNrTrainees())
              .maxTrainees(mentorDto.getMaxTrainees())
              .isTrainingOpen(mentorDto.getIsTrainingOpen())
              .build();
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
