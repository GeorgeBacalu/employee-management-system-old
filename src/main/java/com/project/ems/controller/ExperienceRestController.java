package com.project.ems.controller;

import com.project.ems.entity.Experience;
import com.project.ems.service.ExperienceService;
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
    public ResponseEntity<List<Experience>> findAll() {
        return ResponseEntity.ok(experienceService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Experience> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Experience> save(@RequestBody Experience experience) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.save(experience));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateById(@RequestBody Experience experience, @PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.updateById(experience, id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
