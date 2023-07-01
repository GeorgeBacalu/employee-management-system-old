package com.project.ems.mapper;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMapper {

    public static FeedbackDto convertToDto(ModelMapper modelMapper, Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }

    public static Feedback convertToEntity(ModelMapper modelMapper, FeedbackDto feedbackDto) {
        return modelMapper.map(feedbackDto, Feedback.class);
    }

    public static List<FeedbackDto> convertToDtoList(ModelMapper modelMapper, List<Feedback> feedbacks) {
        return feedbacks.stream().map(feedback -> convertToDto(modelMapper, feedback)).toList();
    }
}
