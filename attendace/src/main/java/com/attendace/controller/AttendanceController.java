package com.attendace.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @CrossOrigin(origins = "*")
    public class AttendanceController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

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
    curl http://localhost:8080/api/getStudentID/123456
    */
    @GetMapping("/getStudentID/{emplid}")
        public ResponseEntity<Student> getStudent(@PathVariable Integer emplid) {
            return studentRepository.findByEmplid(emplid)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }


    //http://localhost:8080/api/getStudentLName/Lovelace
    @GetMapping("/getStudentLName/{studentLName}")
        public ResponseEntity<Student> getStudent(@PathVariable String studentLName) {
            return studentRepository.findByStudentLName(studentLName)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }

    
    //http://localhost:8080/api/getStudentsByCC/CS101
    @GetMapping("/getStudentsByCC/{courseCode}")
        public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String courseCode) {
            List<Student> students = studentRepository.findByCourses_CourseCode(courseCode);
            if (students.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(students); 
        } 


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
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Optional<Student> existing = studentRepository.findByEmplid(student.getEmplid());
        if (existing.isPresent()) {
            return ResponseEntity
                .badRequest()
                .body("Student with this EMPLID already exists.");
        }
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }


    //curl -X DELETE http://localhost:8080/api/deleteStudent/123456
    @DeleteMapping("/deleteStudent/{emplid}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Integer emplid) {
    return studentRepository.findByEmplid(emplid)
            .map(student -> {
                studentRepository.delete(student);
                return ResponseEntity.noContent().build(); // 204 No Content
            })
            .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }

    
    //curl -X DELETE http://localhost:8080/api/deleteStudents
    @DeleteMapping("/deleteStudents")
    public void deleteStudent() {
     studentRepository.deleteAll();
    }

    // STUDENT END | SESSION BEGIN

    //curl http://localhost:8080/api/getSession/byDate/2025-02-01
    @GetMapping("/getSession/byDate/{date}")
        public ResponseEntity<List<Session>> getSessionsByDate( 
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
            List<Session> sessions = sessionRepository.findBySessionDate(date);
            if (sessions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sessions);
        }


    // http://localhost:8080/api/getSession/byCC/CS101
    @GetMapping("/getSession/byCC/{courseCode}")
        public ResponseEntity<List<Session>> getSessionsByCourseCode(@PathVariable String courseCode) {
            List<Session> sessions = sessionRepository.findByCourse_CourseCode(courseCode);
            if (sessions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sessions);
        }


    // http://localhost:8080/api/getAllSessions
    @GetMapping("/getAllSessions")
        public List<Session> getAllSessions() {
            return sessionRepository.findAll();
    }


    //curl http://localhost:8080/api/getSession/2025-02-01/CS101
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
    http://localhost:8080/api/createSession
    '{
        "sessionDate": "2025-02-01",
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


    //curl -X DELETE http://localhost:8080/api/deleteSession/2025-02-01/CS101
    @DeleteMapping("/deleteSession/{date}/{courseCode}")
        public ResponseEntity<Void> deleteSessionByDateAndCourse(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @PathVariable String courseCode) {
            sessionRepository.deleteBySessionDateAndCourse_CourseCode(date, courseCode);
            return ResponseEntity.noContent().build(); 
        }


    // curl -X DELETE http://localhost:8080/api/deleteSessions
    @DeleteMapping("/deleteSessions")
        public ResponseEntity<Void> deleteAllSessions() {
            sessionRepository.deleteAll();
            return ResponseEntity.noContent().build();
        }

    // SESSION END | COURSE BEGIN

    // curl http://localhost:8080/api/getCourse/CS101
    @GetMapping("/getCourse/{courseCode}")
        public ResponseEntity<Course> getCourseByCode(@PathVariable String courseCode) {
            return courseRepository.findByCourseCode(courseCode)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }


    /*
    // curl http://localhost:8080/api/getCourse/Intro%20to%20CS    
    @GetMapping("/getCourse/{courseName}")
        public ResponseEntity<Course> getCourseByName(@PathVariable String courseName) {
            return courseRepository.findByCourseName(courseName)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    */


    // curl http://localhost:8080/api/getCourses
    @GetMapping("/getCourses")
        public List<Course> getAllCourses() {
            return courseRepository.findAll();
        }

    
    /* curl -X POST http://localhost:8080/api/createCourse 
        '{
            "courseCode": "CS101",
            "courseName": "Intro to CS"
        }'
    */
    @PostMapping("/createCourse")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        Optional<Course> existing = courseRepository.findByCourseCode(course.getCourseCode());
        if (existing.isPresent()) {
            return ResponseEntity
                .badRequest()
                .body("Course with this course code already exists.");
        }
        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(saved);
    }


    /*  http://localhost:8080/api/enrollStudents/1
        {
            "studentIds": [1, 2, 3]
        }
    */
    @PostMapping("/enrollStudents/{courseId}")
        public ResponseEntity<Course> enrollStudentsInCourse(
            @PathVariable Integer courseId,
            @RequestBody EnrollmentRequest request) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course == null) return ResponseEntity.notFound().build();
            List<Student> students = studentRepository.findAllById(request.getStudentIds());
            for (Student student : students) {
                if (!student.getCourses().contains(course)) {
                    student.getCourses().add(course);
                }
            }
            studentRepository.saveAll(students);
            return ResponseEntity.ok(course);
        }


    // curl -X DELETE http://localhost:8080/api/deleteCourse/CS101
    @DeleteMapping("/deleteCourse/{courseCode}")
        public ResponseEntity<Void> deleteCourseByCode(@PathVariable String courseCode) {
            courseRepository.deleteByCourseCode(courseCode);
            return ResponseEntity.noContent().build();
        }


    // curl -X DELETE http://localhost:8080/api/deleteCourses
    @DeleteMapping("/deleteCourses")
        public ResponseEntity<Void> deleteAllCourses() {
            courseRepository.deleteAll();
            return ResponseEntity.noContent().build();
        }

    // COURSE END | ATTENDANCE BEGIN

    // curl http://localhost:8080/api/getAttendance/date/2025-02-01/CC/CS101
    @GetMapping("/getAttendance/date/{date}/CC/{courseCode}")
        public ResponseEntity<List<Attendance>> getAttendanceBySession(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String courseCode) {
                Session session = sessionRepository
                    .findBySessionDateAndCourse_CourseCode(date, courseCode)
                    .orElse(null);
                if (session == null) return ResponseEntity.notFound().build();
                return ResponseEntity.ok(attendanceRepository.findBySession(session));
            }


    // curl http://localhost:8080/api/getAttendance/date/2025-02-01/CC/CS101/ID/123456
    @GetMapping("/getAttendance/date/{date}/CC/{courseCode}/ID/{emplid}")
        public ResponseEntity<Attendance> getAttendanceBySessionAndStudent(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String courseCode,
            @PathVariable Integer emplid) {
                Session session = sessionRepository
                    .findBySessionDateAndCourse_CourseCode(date, courseCode)
                    .orElse(null);
                Student student = studentRepository.findByEmplid(emplid).orElse(null);
                if (session == null || student == null) return ResponseEntity.notFound().build();
                return attendanceRepository.findBySessionAndStudent(session, student)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
            }


    // curl http://localhost:8080/api/getAttendance/CC/CS101/ID/123456
    @GetMapping("/getAttendance/CC/{courseCode}/ID/{emplid}")
        public ResponseEntity<Attendance> getAttendanceByCourseAndStudent(
            @PathVariable String courseCode,
            @PathVariable Integer emplid) {
                Course course = courseRepository.findByCourseCode(courseCode).orElse(null);
                Student student = studentRepository.findByEmplid(emplid).orElse(null);
                if (course == null || student == null) return ResponseEntity.notFound().build();
                return attendanceRepository.findByCourseAndStudent(course, student)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
            }


    // curl http://localhost:8080/api/getAttendanceStatus/2025-02-01/CS101/PRESENT
    @GetMapping("/getAttendanceStatus/{date}/{courseCode}/{status}")
        public ResponseEntity<List<Attendance>> getAttendanceBySessionAndStatus(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String courseCode,
            @PathVariable AttendanceStatus status) {
                Session session = sessionRepository
                    .findBySessionDateAndCourse_CourseCode(date, courseCode)
                    .orElse(null);
                if (session == null) return ResponseEntity.notFound().build();
                return ResponseEntity.ok(
                        attendanceRepository.findBySessionAndStatus(session, status)
                );
            }


    //http://localhost:8080/api/getAttendances
    @GetMapping("/getAttendances")
        public ResponseEntity<List<Attendance>> getAllAttendance() {
            List<Attendance> attendanceList = attendanceRepository.findAll();
            if (attendanceList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(attendanceList);
        }


    /* curl -X POST http://localhost:8080/api/createAttendance
        '{
            "sessionDate": "2025-02-01",
            "courseCode": "CS101",
            "emplid": 123456,
            "status": "Present"
        }'
    */
    @PostMapping("/createAttendance")
        public ResponseEntity<Attendance> createAttendance(
            @RequestBody AttendanceRequest request) {
                Session session = sessionRepository
                    .findBySessionDateAndCourse_CourseCode(request.getSessionDate(), request.getCourseCode())
                    .orElse(null);
                if (session == null) {
                    return ResponseEntity.badRequest().build(); 
                }
                Course course = courseRepository.findByCourseCode(request.getCourseCode()).orElse(null);
                if (course == null) {
                    return ResponseEntity.badRequest().build();
                }
                Student student = studentRepository.findByEmplid(request.getEmplid()).orElse(null);
                if (student == null) {
                    return ResponseEntity.badRequest().build();
                }
                Attendance attendance = new Attendance(
                        session,
                        course,
                        student,
                        request.getStatus()
                );
                Attendance saved = attendanceRepository.save(attendance);
                return ResponseEntity.ok(saved);
            }


    // curl -X DELETE http://localhost:8080/api/deleteAttendanceDateCC/2025-02-01/CS101
    @DeleteMapping("/deleteAttendanceDateCC/{date}/{courseCode}")
        public ResponseEntity<Void> deleteAttendanceBySession(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String courseCode) {
                Session session = sessionRepository
                    .findBySessionDateAndCourse_CourseCode(date, courseCode)
                    .orElse(null);
                if (session == null) {
                    return ResponseEntity.notFound().build();
                }
                attendanceRepository.deleteBySession(session);
                return ResponseEntity.noContent().build();
            }


    // curl -X DELETE http://localhost:8080/api/deleteAttendanceDateCCID/2025-02-01/CS101/123456
    @DeleteMapping("/deleteAttendanceDateCCID/{date}/{courseCode}/{emplid}")
        public ResponseEntity<Void> deleteAttendanceBySessionAndStudent(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String courseCode,
            @PathVariable Integer emplid) {
                Session session = sessionRepository
                        .findBySessionDateAndCourse_CourseCode(date, courseCode)
                        .orElse(null);
                Student student = studentRepository.findByEmplid(emplid).orElse(null);
                if (session == null || student == null) {
                    return ResponseEntity.notFound().build();
                }
                attendanceRepository.deleteBySessionAndStudent(session, student);
                return ResponseEntity.noContent().build();
            }


    // curl -X DELETE http://localhost:8080/api/deleteAttendanceCC/CS101
    @DeleteMapping("/deleteAttendanceCC/{courseCode}")
        public ResponseEntity<Void> deleteAttendanceByCourse(@PathVariable String courseCode) {
            Course course = courseRepository.findByCourseCode(courseCode).orElse(null);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            attendanceRepository.deleteByCourse(course);
            return ResponseEntity.noContent().build();
        }


    // curl -X DELETE http://localhost:8080/api/deleteAttendanceCCID/CS101/123456
    @DeleteMapping("/deleteAttendanceCCID/{courseCode}/{emplid}")
        public ResponseEntity<Void> deleteAttendanceByCourseAndStudent(
            @PathVariable String courseCode,
            @PathVariable Integer emplid) {
                Course course = courseRepository.findByCourseCode(courseCode).orElse(null);
                Student student = studentRepository.findByEmplid(emplid).orElse(null);
                if (course == null || student == null) {
                    return ResponseEntity.notFound().build();
                }
                attendanceRepository.deleteByCourseAndStudent(course, student);
                return ResponseEntity.noContent().build();
            }


    // curl -X DELETE http://localhost:8080/api/deleteAttendances
    @DeleteMapping("/deleteAttendances")
        public ResponseEntity<Void> deleteAllAttendance() {
            attendanceRepository.deleteAll();
            return ResponseEntity.noContent().build();
        }


    // curl http://localhost:8080/api/compactDb
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
