package com.example.ruhogwartsschool.service;

import com.example.ruhogwartsschool.entity.Avatar;
import com.example.ruhogwartsschool.exception.FacultyNotFoundExeption;
import com.example.ruhogwartsschool.exception.StudentNotFoundExeption;

import com.example.ruhogwartsschool.entity.Student;
import com.example.ruhogwartsschool.repositories.AvatarRepository;
import com.example.ruhogwartsschool.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
 private  AvatarRepository avatarRepository;

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Value("${Avatars.dir.path}")
    private String avatarsDir;

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

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Avatar readAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = read(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream ok = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bok = new BufferedOutputStream(ok, 1024)
        ) {
            bis.transferTo(bok);

        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
