package com.project.ems.mentor;

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
@RequestMapping(value = "/api/mentors", produces = APPLICATION_JSON_VALUE)
public class MentorRestController implements MentorApi {

    private final MentorService mentorService;

    @Override @GetMapping
    public ResponseEntity<List<MentorDto>> findAll() {
        return ResponseEntity.ok(mentorService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<MentorDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mentorService.findById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorDto> save(@RequestBody @Valid MentorDto mentorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.save(mentorDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorDto> updateById(@RequestBody @Valid MentorDto mentorDto, @PathVariable Integer id) {
        return ResponseEntity.ok(mentorService.updateById(mentorDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        mentorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
