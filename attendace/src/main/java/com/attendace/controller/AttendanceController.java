package com.attendace.controller;

//import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendace.model.Student;
import com.attendace.repository.StudentRepository;


    @RestController
    @RequestMapping("/api")
    public class AttendanceController {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // GET API → Fetch all details
    //Keep this as example endpoint
    @GetMapping("/response")
    public String getAllDetails() {
        String response = ("return response working");
        return response;
    }


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
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    /* 
    
    http://localhost:8080/api/getallStudents
    
    */

    @GetMapping("/getallStudents")
    public List<Student> getAllStudents() {
    return studentRepository.findAll();
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
