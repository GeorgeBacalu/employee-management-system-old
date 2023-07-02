package com.project.ems.feedback;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDto> findAll();

    FeedbackDto findById(Integer id);

    FeedbackDto save(FeedbackDto feedbackDto);

    FeedbackDto updateById(FeedbackDto feedbackDto, Integer id);

    void deleteById(Integer id);
}
