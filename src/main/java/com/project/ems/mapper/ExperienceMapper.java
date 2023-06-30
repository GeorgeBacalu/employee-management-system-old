package com.project.ems.mapper;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceMapper {

    public static ExperienceDto convertToDto(ModelMapper modelMapper, Experience experience) {
        return modelMapper.map(experience, ExperienceDto.class);
    }

    public static Experience convertToEntity(ModelMapper modelMapper, ExperienceDto experienceDto) {
        return modelMapper.map(experienceDto, Experience.class);
    }
}
