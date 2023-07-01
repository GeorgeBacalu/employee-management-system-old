package com.project.ems.mapper;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorDto;
import com.project.ems.mentor.MentorService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MentorMapper {

    public static MentorDto convertToDto(ModelMapper modelMapper, Mentor mentor) {
        MentorDto mentorDto = modelMapper.map(mentor, MentorDto.class);
        Mentor supervisingMentor = mentor.getSupervisingMentor();
        mentorDto.setSupervisingMentorId(supervisingMentor != null ? supervisingMentor.getId() : null);
        mentorDto.setStudiesIds(mentor.getStudies().stream().map(Study::getId).toList());
        mentorDto.setExperiencesIds(mentor.getExperiences().stream().map(Experience::getId).toList());
        return mentorDto;
    }

    public static Mentor convertToEntity(ModelMapper modelMapper, MentorDto mentorDto, RoleService roleService, MentorService mentorService, StudyService studyService, ExperienceService experienceService) {
        Mentor mentor = modelMapper.map(mentorDto, Mentor.class);
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        mentor.setRole(roleService.findEntityById(mentorDto.getRoleId()));
        mentor.setSupervisingMentor(supervisingMentorId != null ? mentorService.findEntityById(supervisingMentorId) : null);
        mentor.setStudies(mentorDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        mentor.setExperiences(mentorDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        return mentor;
    }

    public static List<MentorDto> convertToDtoList(ModelMapper modelMapper, List<Mentor> mentors) {
        return mentors.stream().map(mentor -> convertToDto(modelMapper, mentor)).toList();
    }
}
