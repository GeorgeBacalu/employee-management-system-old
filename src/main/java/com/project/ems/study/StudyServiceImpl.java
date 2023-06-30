package com.project.ems.study;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;

    @Override
    public List<StudyDto> findAll() {
        List<Study> studies = studyRepository.findAll();
        return studies.stream().map(this::convertToDto).toList();
    }

    @Override
    public StudyDto findById(Integer id) {
        Study study = findEntityById(id);
        return convertToDto(study);
    }

    @Override
    public StudyDto save(StudyDto studyDto) {
        Study study = convertToEntity(studyDto);
        Study savedStudy = studyRepository.save(study);
        return convertToDto(savedStudy);
    }

    @Override
    public StudyDto updateById(StudyDto studyDto, Integer id) {
        Study studyToUpdate = findEntityById(id);
        updateEntityFromDto(studyDto, studyToUpdate);
        Study updatedStudy = studyRepository.save(studyToUpdate);
        return convertToDto(updatedStudy);
    }

    @Override
    public void deleteById(Integer id) {
        studyRepository.deleteById(id);
    }

    @Override
    public Study findEntityById(Integer id) {
        return studyRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Study with id %s not found", id)));
    }

    private StudyDto convertToDto(Study study) {
        return StudyDto.builder()
              .id(study.getId())
              .title(study.getTitle())
              .institution(study.getInstitution())
              .description(study.getDescription())
              .type(study.getType())
              .startedAt(study.getStartedAt())
              .finishedAt(study.getFinishedAt())
              .build();
    }

    private Study convertToEntity(StudyDto studyDto) {
        return Study.builder()
              .id(studyDto.getId())
              .title(studyDto.getTitle())
              .institution(studyDto.getInstitution())
              .description(studyDto.getDescription())
              .type(studyDto.getType())
              .startedAt(studyDto.getStartedAt())
              .finishedAt(studyDto.getFinishedAt())
              .build();
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
