package com.project.ems.mentor;

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
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorRestController {

    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<Mentor>> findAll() {
        return ResponseEntity.ok(mentorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mentor> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mentorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Mentor> save(@RequestBody Mentor mentor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.save(mentor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mentor> updateById(@RequestBody Mentor mentor, @PathVariable Integer id) {
        return ResponseEntity.ok(mentorService.updateById(mentor, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        mentorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
