package com.example.ruhogwartsschool.service;

import com.example.ruhogwartsschool.exception.FacultyNotFoundExeption;
import com.example.ruhogwartsschool.exception.StudentNotFoundExeption;

import com.example.ruhogwartsschool.entity.Student;
import com.example.ruhogwartsschool.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {

       return studentRepository.save(student);
    }


        public Student read(Long id) {
            return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundExeption(id));
        }


        public Student update(Long id, Student student) {
            Student oldStudent= studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundExeption(id));
            oldStudent.setName(student.getName());
            oldStudent.setAge(student.getAge());
            return studentRepository.save(oldStudent);
        };


        public  void delete(Long id) {
            Student student = studentRepository.findById(id).orElseThrow(()-> new FacultyNotFoundExeption(id));
            studentRepository.delete(student);

        }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findAllByAge(age).stream()
                .filter(student -> student.getAge()==age)
                .collect(Collectors.toList());
    }

}
