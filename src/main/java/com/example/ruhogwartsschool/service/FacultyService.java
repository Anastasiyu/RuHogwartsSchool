package com.example.ruhogwartsschool.service;

import com.example.ruhogwartsschool.exception.FacultyNotFoundExeption;
import com.example.ruhogwartsschool.entity.Faculty;
import com.example.ruhogwartsschool.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


        public Faculty read(Long id) {
    return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundExeption(id));
    }


   	    public Faculty update(Long id, Faculty faculty) {
            Faculty oldFaculty= facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundExeption(id));
            oldFaculty.setName(faculty.getName());
            oldFaculty.setColor(faculty.getColor());
        return facultyRepository.save(oldFaculty);
    };


       public  void delete(Long id) {
           Faculty faculty = facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundExeption(id));
           facultyRepository.delete(faculty);

    }


    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findAllByColor(color).stream()
                .filter(student -> student.getColor()==color)
                .collect(Collectors.toList());
    }

    public Collection<Faculty> findByNameOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }



}
