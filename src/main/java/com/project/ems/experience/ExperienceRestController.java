package com.project.ems.experience;

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

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceRestController {

    private final ExperienceService experienceService;
    
    @GetMapping
    public ResponseEntity<List<ExperienceDto>> findAll() {
        return ResponseEntity.ok(experienceService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<ExperienceDto> save(@RequestBody @Valid ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.save(experienceDto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDto> updateById(@RequestBody @Valid ExperienceDto experienceDto, @PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.updateById(experienceDto, id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
