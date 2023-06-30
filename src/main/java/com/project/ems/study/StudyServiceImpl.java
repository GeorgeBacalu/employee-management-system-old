package com.project.ems.study;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.mapper.StudyMapper.convertToDto;
import static com.project.ems.mapper.StudyMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudyDto> findAll() {
        List<Study> studies = studyRepository.findAll();
        return studies.stream().map(study -> convertToDto(modelMapper, study)).toList();
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
        studyRepository.deleteById(id);
    }

    @Override
    public Study findEntityById(Integer id) {
        return studyRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Study with id %s not found", id)));
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
