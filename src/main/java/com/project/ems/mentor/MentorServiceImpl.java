package com.project.ems.mentor;

import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.StudyService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;
import static com.project.ems.mapper.MentorMapper.convertToDto;
import static com.project.ems.mapper.MentorMapper.convertToDtoList;
import static com.project.ems.mapper.MentorMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final StudyService studyService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;

    @Override
    public List<MentorDto> findAll() {
        List<Mentor> mentors = mentorRepository.findAll();
        return !mentors.isEmpty() ? convertToDtoList(modelMapper, mentors) : new ArrayList<>();
    }

    @Override
    public MentorDto findById(Integer id) {
        Mentor mentor = findEntityById(id);
        return convertToDto(modelMapper, mentor);
    }

    @Override
    public MentorDto save(MentorDto mentorDto) {
        Mentor mentor = convertToEntity(modelMapper, mentorDto, roleService, this, studyService, experienceService);
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
        Mentor mentorToDelete = findEntityById(id);
        mentorRepository.findAllBySupervisingMentor(mentorToDelete).forEach(mentor -> mentor.setSupervisingMentor(null));
        employeeRepository.findAllByMentor(mentorToDelete).forEach(employee -> employee.setMentor(null));
        mentorRepository.delete(mentorToDelete);
    }

    @Override
    public Page<MentorDto> findAllByKey(Pageable pageable, String key) {
        Page<Mentor> mentorsPage = key.trim().equals("") ? mentorRepository.findAll(pageable) : mentorRepository.findAllByKey(pageable, key.toLowerCase());
        return mentorsPage.hasContent() ? mentorsPage.map(mentor -> convertToDto(modelMapper, mentor)) : Page.empty();
    }

    @Override
    public Mentor findEntityById(Integer id) {
        return mentorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, id)));
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
        mentor.setStudies(mentorDto.getStudiesIds().stream().map(studyService::findEntityById).collect(Collectors.toList()));
        mentor.setExperiences(mentorDto.getExperiencesIds().stream().map(experienceService::findEntityById).collect(Collectors.toList()));
        mentor.setNrTrainees(mentorDto.getNrTrainees());
        mentor.setMaxTrainees(mentorDto.getMaxTrainees());
        mentor.setIsTrainingOpen(mentorDto.getIsTrainingOpen());
    }
}
