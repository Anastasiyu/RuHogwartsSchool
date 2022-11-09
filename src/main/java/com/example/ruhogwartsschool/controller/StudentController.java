package com.example.ruhogwartsschool.controller;

import com.example.ruhogwartsschool.entity.Avatar;
import com.example.ruhogwartsschool.entity.Student;
import com.example.ruhogwartsschool.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody @Valid Student student) {
        Student created = studentService.create(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("{id}")
    public Student read(@PathVariable long id) {
        return studentService.readStudent(id);
    }


    @PutMapping("{id}")
    public Student update(@PathVariable long id,
                          @RequestBody @Valid Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {

         studentService.delete(id);
    }

    @GetMapping
    public Collection<Student> findByAge(@RequestParam int age) {

        return studentService.findByAge(age);
    }

    @GetMapping(value = "/findStudentsBetweenAge")
    public ResponseEntity<Collection<Student>> findStudentsBetweenAge(@RequestParam int min,
                                                                      @RequestParam int max) {
        if (min > 0 && max > 0 && max > min) {
           return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
        }
       return ResponseEntity.ok(Collections.emptyList());
    }


}
