package com.project.ems.feedback;

import com.project.ems.exception.ResourceNotFoundException;
import com.project.ems.user.UserService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.ems.constants.ExceptionMessageConstants.FEEDBACK_NOT_FOUND;

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
        return !feedbacks.isEmpty() ? convertToDtos(feedbacks) : new ArrayList<>();
    }

    @Override
    public FeedbackDto findById(Integer id) {
        Feedback feedback = findEntityById(id);
        return convertToDto(feedback);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = convertToEntity(feedbackDto);
        feedback.setSentAt(LocalDateTime.now(clock));
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
        Feedback feedbackToDelete = findEntityById(id);
        feedbackRepository.delete(feedbackToDelete);
    }

    @Override
    public Page<FeedbackDto> findAllByKey(Pageable pageable, String key) {
        Page<Feedback> feedbacksPage = key.trim().equals("") ? feedbackRepository.findAll(pageable) : feedbackRepository.findAllByKey(pageable, key.toLowerCase());
        return feedbacksPage.hasContent() ? feedbacksPage.map(this::convertToDto) : Page.empty();
    }

    @Override
    public List<FeedbackDto> convertToDtos(List<Feedback> feedbacks) {
        return feedbacks.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Feedback> convertToEntities(List<FeedbackDto> feedbackDtos) {
        return feedbackDtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public FeedbackDto convertToDto(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }

    @Override
    public Feedback convertToEntity(FeedbackDto feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        feedback.setUser(userService.findEntityById(feedbackDto.getUserId()));
        return feedback;
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
