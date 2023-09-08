package com.project.ems.feedback;

import com.project.ems.wrapper.PageWrapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/feedbacks", produces = APPLICATION_JSON_VALUE)
public class FeedbackRestController implements FeedbackApi {

    private final FeedbackService feedbackService;

    @Override @GetMapping
    public ResponseEntity<List<FeedbackDto>> findAll() {
        return ResponseEntity.ok(feedbackService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedbackDto> save(@RequestBody @Valid FeedbackDto feedbackDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.save(feedbackDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedbackDto> updateById(@RequestBody @Valid FeedbackDto feedbackDto, @PathVariable Integer id) {
        return ResponseEntity.ok(feedbackService.updateById(feedbackDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<PageWrapper<FeedbackDto>> findAllByKey(@PageableDefault(size = 2, sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(new PageWrapper<>(feedbackService.findAllByKey(pageable, key).getContent()));
    }
}
