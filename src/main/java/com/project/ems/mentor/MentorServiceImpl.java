package com.project.ems.mentor;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;

    @Override
    public List<Mentor> findAll() {
        return mentorRepository.findAll();
    }

    @Override
    public Mentor findById(Integer id) {
        return mentorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Mentor with id %s not found", id)));
    }

    @Override
    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    @Override
    public Mentor updateById(Mentor mentor, Integer id) {
        Mentor mentorToUpdate = findById(id);
        mentorToUpdate.setName(mentor.getName());
        mentorToUpdate.setEmail(mentor.getEmail());
        mentorToUpdate.setPassword(mentor.getPassword());
        mentorToUpdate.setMobile(mentor.getMobile());
        mentorToUpdate.setAddress(mentor.getAddress());
        mentorToUpdate.setBirthday(mentor.getBirthday());
        mentorToUpdate.setRole(mentor.getRole());
        mentorToUpdate.setEmploymentType(mentor.getEmploymentType());
        mentorToUpdate.setPosition(mentor.getPosition());
        mentorToUpdate.setGrade(mentor.getGrade());
        mentorToUpdate.setSupervisingMentor(mentor.getSupervisingMentor());
        mentorToUpdate.setStudies(mentor.getStudies());
        mentorToUpdate.setExperiences(mentor.getExperiences());
        mentorToUpdate.setNrTrainees(mentor.getNrTrainees());
        mentorToUpdate.setMaxTrainees(mentor.getMaxTrainees());
        mentorToUpdate.setIsTrainingOpen(mentor.getIsTrainingOpen());
        return mentorRepository.save(mentorToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        mentorRepository.deleteById(id);
    }
}
