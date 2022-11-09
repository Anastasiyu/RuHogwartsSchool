package com.example.ruhogwartsschool.controller;

import com.example.ruhogwartsschool.entity.Student;
import com.example.ruhogwartsschool.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/students")
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
        return studentService.read(id);
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


}
