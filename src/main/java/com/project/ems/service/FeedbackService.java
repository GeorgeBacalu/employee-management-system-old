package com.project.ems.service;

import com.project.ems.entity.Feedback;
import java.util.List;

public interface FeedbackService {

    List<Feedback> findAll();

    Feedback findById(Integer id);

    Feedback save(Feedback feedback);

    Feedback updateById(Feedback feedback, Integer id);

    void deleteById(Integer id);
}
