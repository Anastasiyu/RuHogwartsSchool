package com.example.ruhogwartsschool.repositories;


import com.example.ruhogwartsschool.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    Collection<Faculty>  findAllByColor(String color);
    Collection<Faculty> findByNameOrColorIgnoreCase(String name, String color);
}
