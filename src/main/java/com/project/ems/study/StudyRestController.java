package com.project.ems.study;

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
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyRestController {

    private final StudyService studyService;

    @GetMapping
    public ResponseEntity<List<Study>> findAll() {
        return ResponseEntity.ok(studyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Study> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(studyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Study> save(@RequestBody Study study) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyService.save(study));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Study> updateById(@RequestBody Study study, @PathVariable Integer id) {
        return ResponseEntity.ok(studyService.updateById(study, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
