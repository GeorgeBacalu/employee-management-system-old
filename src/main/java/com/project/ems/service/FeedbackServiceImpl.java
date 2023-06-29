package com.project.ems.service;

import com.project.ems.entity.Feedback;
import com.project.ems.repository.FeedbackRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findById(Integer id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Feedback with id %s not found", id)));
    }

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateById(Feedback feedback, Integer id) {
        Feedback feedbackToUpdate = findById(id);
        feedbackToUpdate.setType(feedback.getType());
        feedbackToUpdate.setDescription(feedback.getDescription());
        feedbackToUpdate.setSentAt(feedback.getSentAt());
        feedbackToUpdate.setUser(feedback.getUser());
        return feedbackRepository.save(feedbackToUpdate);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.deleteById(id);
    }
}
