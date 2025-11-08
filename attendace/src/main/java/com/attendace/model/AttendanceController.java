package com.attendace.model;

//import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendace.model.dao.StudentDAOImpl;


    @RestController
    @RequestMapping("/api")
public class AttendanceController {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    StudentDAOImpl studentdaoimpl = new StudentDAOImpl(jdbcTemplate);

    // GET API → Fetch all details
    @GetMapping("/response")
    public String getAllDetails() {
        String response = ("return response");
        return response;
    }

    // GET
    @GetMapping("/getstudent")
    public Student getStudent() {
        Student student = new Student();
        student.setStudentFName("John");
        //studentdaoimpl.addStudent(student);
        return student;
    }


    // POST student
    @PostMapping("/poststudent")
    public Student postStudent(@RequestBody Student student) {
        student.setStudentId(1);
        return student;
    }


    // DELETE student
    @DeleteMapping("/deletestudent/{id}")
    public String deleteStudent(@PathVariable int id) {
        return "Student with ID " + id + " has been deleted.";
    }

    // PUT






    /*  @GetMapping("/fetchstudents")
    public List<Student> saveStudent() {
        return studentdaoimpl.getAllStudents();
        
    } */
}
