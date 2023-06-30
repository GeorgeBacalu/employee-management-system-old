package com.project.ems.role;

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
@RequestMapping(value = "/api/roles", produces = APPLICATION_JSON_VALUE)
public class RoleRestController implements RoleApi {

    private final RoleService roleService;

    @Override @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Override @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @Override @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> save(@RequestBody @Valid RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleDto));
    }

    @Override @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> updateById(@RequestBody @Valid RoleDto roleDto, @PathVariable Integer id) {
        return ResponseEntity.ok(roleService.updateById(roleDto, id));
    }

    @Override @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
