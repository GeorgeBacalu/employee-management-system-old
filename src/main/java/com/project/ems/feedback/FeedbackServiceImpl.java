package com.project.ems.feedback;

import com.project.ems.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.project.ems.mapper.FeedbackMapper.convertToDto;
import static com.project.ems.mapper.FeedbackMapper.convertToEntity;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream().map(feedback -> convertToDto(modelMapper, feedback)).toList();
    }

    @Override
    public FeedbackDto findById(Integer id) {
        Feedback feedback = findEntityById(id);
        return convertToDto(modelMapper, feedback);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = convertToEntity(modelMapper, feedbackDto);
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
        feedbackRepository.deleteById(id);
    }

    private Feedback findEntityById(Integer id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Feedback with id %s not found", id)));
    }

    private void updateEntityFromDto(FeedbackDto feedbackDto, Feedback feedback) {
        feedback.setType(feedbackDto.getType());
        feedback.setDescription(feedbackDto.getDescription());
        feedback.setSentAt(feedbackDto.getSentAt());
        feedback.setUser(userService.findEntityById(feedbackDto.getUserId()));
    }
}
