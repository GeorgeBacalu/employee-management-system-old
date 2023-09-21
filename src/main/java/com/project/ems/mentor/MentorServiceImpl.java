package com.project.ems.mentor;

import com.project.ems.employee.EmployeeRepository;
import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceService;
import com.project.ems.role.RoleService;
import com.project.ems.study.Study;
import com.project.ems.study.StudyService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.MENTOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final StudyService studyService;
    private final ExperienceService experienceService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<MentorDto> findAll() {
        List<Mentor> mentors = mentorRepository.findAll();
        return !mentors.isEmpty() ? convertToDtos(mentors) : new ArrayList<>();
    }

    @Override
    public MentorDto findById(Integer id) {
        Mentor mentor = findEntityById(id);
        return convertToDto(mentor);
    }

    @Override
    public MentorDto save(MentorDto mentorDto) {
        Mentor mentor = convertToEntity(mentorDto);
        mentor.setPassword(passwordEncoder.encode(mentor.getPassword()));
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
        Mentor mentorToDelete = findEntityById(id);
        mentorRepository.findAllBySupervisingMentor(mentorToDelete).forEach(mentor -> mentor.setSupervisingMentor(null));
        employeeRepository.findAllByMentor(mentorToDelete).forEach(employee -> employee.setMentor(null));
        mentorRepository.delete(mentorToDelete);
    }

    @Override
    public Page<MentorDto> findAllByKey(Pageable pageable, String key) {
        Page<Mentor> mentorsPage = key.trim().equals("") ? mentorRepository.findAll(pageable) : mentorRepository.findAllByKey(pageable, key.toLowerCase());
        return mentorsPage.hasContent() ? mentorsPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public List<MentorDto> convertToDtos(List<Mentor> mentors) {
        return mentors.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Mentor> convertToEntities(List<MentorDto> mentorDtos) {
        return mentorDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public MentorDto convertToDto(Mentor mentor) {
        MentorDto mentorDto = modelMapper.map(mentor, MentorDto.class);
        Mentor supervisingMentor = mentor.getSupervisingMentor();
        mentorDto.setSupervisingMentorId(supervisingMentor != null ? supervisingMentor.getId() : null);
        mentorDto.setStudiesIds(mentor.getStudies().stream().map(Study::getId).toList());
        mentorDto.setExperiencesIds(mentor.getExperiences().stream().map(Experience::getId).toList());
        return mentorDto;
    }

    @Override
    public Mentor convertToEntity(MentorDto mentorDto) {
        Mentor mentor = modelMapper.map(mentorDto, Mentor.class);
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        mentor.setRole(roleService.findEntityById(mentorDto.getRoleId()));
        mentor.setSupervisingMentor(supervisingMentorId != null ? this.findEntityById(supervisingMentorId) : null);
        mentor.setStudies(mentorDto.getStudiesIds().stream().map(studyService::findEntityById).toList());
        mentor.setExperiences(mentorDto.getExperiencesIds().stream().map(experienceService::findEntityById).toList());
        return mentor;
    }

    @Override
    public Mentor findEntityById(Integer id) {
        return mentorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(MENTOR_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(MentorDto mentorDto, Mentor mentor) {
        Integer supervisingMentorId = mentorDto.getSupervisingMentorId();
        mentor.setName(mentorDto.getName());
        mentor.setEmail(mentorDto.getEmail());
        mentor.setPassword(passwordEncoder.encode(mentorDto.getPassword()));
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
        mentor.setOpenForTraining(mentorDto.getOpenForTraining());
    }
}
