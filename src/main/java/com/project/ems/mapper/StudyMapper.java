package com.project.ems.mapper;

import com.project.ems.study.Study;
import com.project.ems.study.StudyDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMapper {

    public static StudyDto convertToDto(ModelMapper modelMapper, Study study) {
        return modelMapper.map(study, StudyDto.class);
    }

    public static Study convertToEntity(ModelMapper modelMapper, StudyDto studyDto) {
        return modelMapper.map(studyDto, Study.class);
    }
}
