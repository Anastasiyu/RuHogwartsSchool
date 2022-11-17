package com.example.ruhogwartsschool.repositories;


import com.example.ruhogwartsschool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student>findAllByAge(int age);
    Collection<Student> findByAgeBetween(int min, int max);
}
