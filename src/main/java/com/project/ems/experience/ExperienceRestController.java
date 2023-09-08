package com.project.ems.experience;

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
@RequestMapping(value = "/api/experiences", produces = APPLICATION_JSON_VALUE)
public class ExperienceRestController implements ExperienceApi {

    private final ExperienceService experienceService;
    
    @Override @GetMapping
    public ResponseEntity<List<ExperienceDto>> findAll() {
        return ResponseEntity.ok(experienceService.findAll());
    }
    
    @Override @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.findById(id));
    }
    
    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExperienceDto> save(@RequestBody @Valid ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.save(experienceDto));
    }
    
    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExperienceDto> updateById(@RequestBody @Valid ExperienceDto experienceDto, @PathVariable Integer id) {
        return ResponseEntity.ok(experienceService.updateById(experienceDto, id));
    }
    
    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        experienceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override @GetMapping("/pagination")
    public ResponseEntity<PageWrapper<ExperienceDto>> findAllByKey(@PageableDefault(size = 2, sort = "id") Pageable pageable, @RequestParam(required = false, defaultValue = "") String key) {
        return ResponseEntity.ok(new PageWrapper<>(experienceService.findAllByKey(pageable, key).getContent()));
    }
}
