package com.project.ems.controller;

import com.project.ems.entity.Feedback;
import com.project.ems.service.FeedbackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackRestController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> findAll() {
        return ResponseEntity.ok(feedbackService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Feedback> save(@RequestBody Feedback feedback) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.save(feedback));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateById(@RequestBody Feedback feedback, @PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.updateById(feedback, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
