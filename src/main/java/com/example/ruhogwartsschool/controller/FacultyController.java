package com.example.ruhogwartsschool.controller;

import com.example.ruhogwartsschool.entity.Faculty;
import com.example.ruhogwartsschool.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;


@RestController
    @RequestMapping("/faculty")
    public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody @Valid Faculty faculty) {
        Faculty created = facultyService.create(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("{id}")
    public Faculty read(@PathVariable long id) {
        return facultyService.read(id);
    }


    @PutMapping("{id}")
    public Faculty update(@PathVariable long id,
                          @RequestBody @Valid Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {

        facultyService.delete(id);
    }

    @GetMapping
    public Collection<Faculty> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }


   @GetMapping(value = "/findFaculty")
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
       return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping(value = "/ findByNameOrColorIgnoreCase")
    public ResponseEntity<Collection<Faculty>> findByNameOrColorIgnoreCase(@RequestParam(required = false) String string) {
        if (string != null && !string.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameOrColorIgnoreCase(string, string));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}