package com.project.ems.feedback;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    List<FeedbackDto> findAll();

    FeedbackDto findById(Integer id);

    FeedbackDto save(FeedbackDto feedbackDto);

    FeedbackDto updateById(FeedbackDto feedbackDto, Integer id);

    void deleteById(Integer id);

    Page<FeedbackDto> findAllByKey(Pageable pageable, String key);
}
