package com.project.ems.authority;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/api/authorities", produces = APPLICATION_JSON_VALUE)
public class AuthorityRestController implements AuthorityApi {

    private final AuthorityService authorityService;

    @Override @GetMapping
    public ResponseEntity<List<AuthorityDto>> findAll() {
        return ResponseEntity.ok(authorityService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<AuthorityDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(authorityService.findById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityDto> save(@RequestBody AuthorityDto authorityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorityService.save(authorityDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityDto> updateById(@RequestBody @Valid AuthorityDto authorityDto, @PathVariable Integer id) {
        return ResponseEntity.ok(authorityService.updateById(authorityDto, id));
    }
}
