package com.attendace.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendace.model.*;
import com.attendace.repository.*;
import com.attendace.dto.*;

/* 
import com.attendace.model.Student;
import com.attendace.model.Session;
import com.attendace.model.Course;
import com.attendace.model.Attendance; 

import com.attendace.repository.StudentRepository;
import com.attendace.repository.SessionRepository;
import com.attendace.repository.CourseRepository;
import com.attendace.repository.AttendanceRepository;
*/


    @RestController
    @RequestMapping("/api")
    public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /* 
    @Autowired
    private AttendanceRepository attendanceRepository;
    */
    @Autowired
    private CourseRepository courseRepository;
    

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // GET API → Fetch all details
    //Keep this as example endpoint
    @GetMapping("/response")
    public String getAllDetails() {
        String response = ("return response working");
        return response;
    }


    // STUDENT BEGIN

    /* 
    This is for fetching a specific student.
    For example: 
    curl http://localhost:8080/api/getStudent/123456
    */
   @GetMapping("/getStudent/{emplid}")
        public ResponseEntity<Student> getStudent(@PathVariable Integer emplid) {
            return studentRepository.findByEmplid(emplid)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }


    @GetMapping("/getStudent/{studentLName}")
        public ResponseEntity<Student> getStudent(@PathVariable String studentLName) {
            return studentRepository.findByStudentLName(studentLName)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }


    /* @GetMapping("/getStudentsByCourse/{courseCode}")
        public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String courseCode) {
            List<Student> students = studentRepository.findByCourse_CourseCode(courseCode);
            if (students.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(students); 
        } */


    // http://localhost:8080/api/getAllStudents
    @GetMapping("/getAllStudents")
        public List<Student> getAllStudents() {
            return studentRepository.findAll();
    }


    /* 
     curl -X POST http://localhost:8080/api/createStudent   -H "Content-Type: application/json"   -d 
     '{
        "emplid": 123456,
        "studentFName": "Ada",
        "studentLName": "Lovelace",
        "studentEmail": "ada.lovelace@example.com"
      }'
    */
    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }


   @DeleteMapping("/deleteStudent/{emplid}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Integer emplid) {
    return studentRepository.findByEmplid(emplid)
            .map(student -> {
                studentRepository.delete(student);
                return ResponseEntity.noContent().build(); // 204 No Content
            })
            .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }

    
   @DeleteMapping("/deleteStudents")
    public void deleteStudent() {
     studentRepository.deleteAll();
    }

    // STUDENT END | SESSION BEGIN

    @GetMapping("/getSession/{date}")
        public ResponseEntity<List<Session>> getSessionsByDate( 
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
            List<Session> sessions = sessionRepository.findBySessionDate(date);
            if (sessions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sessions);
        }


    /* 
    @GetMapping("/getSession/{courseCode}")
        public ResponseEntity<List<Session>> getSessionsByCourseCode(@PathVariable String courseCode) {
            List<Session> sessions = sessionRepository.findByCourse_CourseCode(courseCode);
            if (sessions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sessions);
        }
    */


    // http://localhost:8080/api/getAllSessions
    @GetMapping("/getAllSessions")
        public List<Session> getAllSessions() {
            return sessionRepository.findAll();
    }


    @GetMapping("/getSession/{date}/{courseCode}")
        public ResponseEntity<Session> getSessionByDateAndCourse(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @PathVariable String courseCode) {
            Optional<Session> sessionOpt =
            sessionRepository.findBySessionDateAndCourse_CourseCode(date, courseCode);
            if (sessionOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        return ResponseEntity.ok(sessionOpt.get());
        }
    

    /*
    '{
        "sessionDate": "2025-01-28",
        "courseCode": "CS101"
    ''}
    */
    @PostMapping("/createSession")
        public ResponseEntity<Session> createSession(@RequestBody SessionRequest request) {
            Course course = courseRepository.findByCourseCode(request.getCourseCode())
                .orElse(null);
            if (course == null) {
            return ResponseEntity.badRequest().build();
        }
        Session session = new Session(request.getSessionDate(), course);
        Session saved = sessionRepository.save(session);
        return ResponseEntity.ok(saved);
        }


    



















    @GetMapping("/compactDb")
    public ResponseEntity<String> compactDatabase() {
        try {
            jdbcTemplate.execute("VACUUM;");
            return ResponseEntity.ok("Database compacted successfully ✅");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error compacting database: " + e.getMessage());
        }
    }

}
