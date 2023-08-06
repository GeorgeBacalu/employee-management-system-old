package com.project.ems.study;

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

import static com.project.ems.constants.ExceptionMessageConstants.STUDY_NOT_FOUND;
import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToDtoList;
import static com.project.ems.mapper.StudyMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final EmployeeRepository employeeRepository;
    private final MentorRepository mentorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudyDto> findAll() {
        List<Study> studies = studyRepository.findAll();
        return !studies.isEmpty() ? convertToDtoList(modelMapper, studies) : new ArrayList<>();
    }

    @Override
    public StudyDto findById(Integer id) {
        Study study = findEntityById(id);
        return convertToDto(modelMapper, study);
    }

    @Override
    public StudyDto save(StudyDto studyDto) {
        Study study = convertToEntity(modelMapper, studyDto);
        Study savedStudy = studyRepository.save(study);
        return convertToDto(modelMapper, savedStudy);
    }

    @Override
    public StudyDto updateById(StudyDto studyDto, Integer id) {
        Study studyToUpdate = findEntityById(id);
        updateEntityFromDto(studyDto, studyToUpdate);
        Study updatedStudy = studyRepository.save(studyToUpdate);
        return convertToDto(modelMapper, updatedStudy);
    }

    @Override
    public void deleteById(Integer id) {
        Study studyToDelete = findEntityById(id);
        employeeRepository.findAllByStudiesContains(studyToDelete).forEach(employee -> employee.getStudies().remove(studyToDelete));
        mentorRepository.findAllByStudiesContains(studyToDelete).forEach(mentor -> mentor.getStudies().remove(studyToDelete));
        studyRepository.delete(studyToDelete);
    }

    @Override
    public Page<StudyDto> findAllByKey(Pageable pageable, String key) {
        Page<Study> studiesPage = key.trim().equals("") ? studyRepository.findAll(pageable) : studyRepository.findAllByKey(pageable, key.toLowerCase());
        return studiesPage.hasContent() ? studiesPage.map(study -> convertToDto(modelMapper, study)) : Page.empty();
    }

    @Override
    public Study findEntityById(Integer id) {
        return studyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(STUDY_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(StudyDto studyDto, Study study) {
        study.setTitle(studyDto.getTitle());
        study.setInstitution(studyDto.getInstitution());
        study.setDescription(studyDto.getDescription());
        study.setType(studyDto.getType());
        study.setStartedAt(studyDto.getStartedAt());
        study.setFinishedAt(studyDto.getFinishedAt());
    }
}
