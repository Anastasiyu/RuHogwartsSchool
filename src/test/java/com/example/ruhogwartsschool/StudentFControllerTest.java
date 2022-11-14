package com.example.ruhogwartsschool;

import com.example.ruhogwartsschool.controller.StudentController;
import com.example.ruhogwartsschool.entity.Student;
import com.example.ruhogwartsschool.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentFControllerTest {
    @LocalServerPort
    private  int port;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
            void contexLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent()throws  Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" +port+ "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent()throws  Exception{
        Student student = new Student(1, "Иванов Иван", 21);
        student.setName("Иванов Иван");
        student.setAge(21);

        assertThat(this.restTemplate.patchForObject("http://localhost:" +port+ "/student", student, String.class))
                .isNotNull();
    }


}
