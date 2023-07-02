package com.project.ems.study;

import jakarta.validation.Valid;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/studies", produces = APPLICATION_JSON_VALUE)
public class StudyRestController implements StudyApi {

    private final StudyService studyService;

    @Override @GetMapping
    public ResponseEntity<List<StudyDto>> findAll() {
        return ResponseEntity.ok(studyService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<StudyDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(studyService.findById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudyDto> save(@RequestBody @Valid StudyDto studyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyService.save(studyDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<StudyDto> updateById(@RequestBody @Valid StudyDto studyDto, @PathVariable Integer id) {
        return ResponseEntity.ok(studyService.updateById(studyDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
