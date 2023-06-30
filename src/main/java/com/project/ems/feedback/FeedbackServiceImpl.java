package com.project.ems.feedback;

import com.project.ems.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;

    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream().map(this::convertToDto).toList();
    }

    @Override
    public FeedbackDto findById(Integer id) {
        Feedback feedback = findEntityById(id);
        return convertToDto(feedback);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = convertToEntity(feedbackDto);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return convertToDto(savedFeedback);
    }

    @Override
    public FeedbackDto updateById(FeedbackDto feedbackDto, Integer id) {
        Feedback feedbackToUpdate = findEntityById(id);
        updateEntityFromDto(feedbackDto, feedbackToUpdate);
        Feedback updatedFeedback = feedbackRepository.save(feedbackToUpdate);
        return convertToDto(updatedFeedback);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.deleteById(id);
    }

    private Feedback findEntityById(Integer id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Feedback with id %s not found", id)));
    }

    private FeedbackDto convertToDto(Feedback feedback) {
        return FeedbackDto.builder()
              .id(feedback.getId())
              .type(feedback.getType())
              .description(feedback.getDescription())
              .sentAt(feedback.getSentAt())
              .userId(feedback.getUser().getId())
              .build();
    }

    private Feedback convertToEntity(FeedbackDto feedbackDto) {
        return Feedback.builder()
              .id(feedbackDto.getUserId())
              .type(feedbackDto.getType())
              .description(feedbackDto.getDescription())
              .sentAt(feedbackDto.getSentAt())
              .user(userService.findEntityById(feedbackDto.getUserId()))
              .build();
    }

    private void updateEntityFromDto(FeedbackDto feedbackDto, Feedback feedback) {
        feedback.setType(feedbackDto.getType());
        feedback.setDescription(feedbackDto.getDescription());
        feedback.setSentAt(feedbackDto.getSentAt());
        feedback.setUser(userService.findEntityById(feedbackDto.getUserId()));
    }
}
