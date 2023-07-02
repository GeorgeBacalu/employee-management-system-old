package com.project.ems.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;
import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToDtoList;
import static com.project.ems.mapper.FeedbackMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Clock clock;

    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return convertToDtoList(modelMapper, feedbacks);
    }

    @Override
    public FeedbackDto findById(Integer id) {
        Feedback feedback = findEntityById(id);
        return convertToDto(modelMapper, feedback);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = convertToEntity(modelMapper, feedbackDto);
        feedback.setSentAt(LocalDateTime.now(clock));
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return convertToDto(modelMapper, savedFeedback);
    }

    @Override
    public FeedbackDto updateById(FeedbackDto feedbackDto, Integer id) {
        Feedback feedbackToUpdate = findEntityById(id);
        updateEntityFromDto(feedbackDto, feedbackToUpdate);
        Feedback updatedFeedback = feedbackRepository.save(feedbackToUpdate);
        return convertToDto(modelMapper, updatedFeedback);
    }

    @Override
    public void deleteById(Integer id) {
        Feedback feedbackToDelete = findEntityById(id);
        feedbackRepository.delete(feedbackToDelete);
    }

    private Feedback findEntityById(Integer id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
    }

    private void updateEntityFromDto(FeedbackDto feedbackDto, Feedback feedback) {
        feedback.setType(feedbackDto.getType());
        feedback.setDescription(feedbackDto.getDescription());
        feedback.setUser(userService.findEntityById(feedbackDto.getUserId()));
    }
}
