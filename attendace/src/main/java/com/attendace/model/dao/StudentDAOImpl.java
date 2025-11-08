package com.attendace.model.dao;

import com.attendace.model.Student;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;
    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO Student (EMPLID, StudentFName, StudentLName, StudentEmail) VALUES (?, ?, ?, ?)"; //SQL statement that inserts one new row into the Student table
        jdbcTemplate.update(sql,                                                                                   //Executes the command to fill the placehholders in the newly inserted row
            student.getEmplid(),
            student.getStudentFName(),
            student.getStudentLName(),
            student.getStudentEmail()
        );
    }

    @Override
    public Student getStudentByID(int studentId) {
        String sql = "SELECT * FROM Student WHERE StudentID = ?";                   //defines SQL query to run
                                                                                    //"?" is a parameter placeholder to prevent SQL injection
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {                   
            Student student = new Student();
            student.setStudentId(rs.getInt("StudentID"));
            student.setEmplid(rs.getInt("EMPLID"));
            student.setStudentFName(rs.getString("StudentFName"));
            student.setStudentLName(rs.getString("StudentLName"));
            student.setStudentEmail(rs.getString("StudentEmail"));
            return student;
        }, studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM Student";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Student student = new Student();
            student.setStudentId(rs.getInt("StudentID"));
            student.setEmplid(rs.getInt("EMPLID"));
            student.setStudentFName(rs.getString("StudentFName"));
            student.setStudentLName(rs.getString("StudentLName"));
            student.setStudentEmail(rs.getString("StudentEmail"));
            return student;
        });
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE Student SET EMPLID = ?, StudentFName = ?, StudentLName = ?, StudentEmail = ? WHERE StudentID = ?";
        jdbcTemplate.update(sql,
            student.getEmplid(),
            student.getStudentFName(),
            student.getStudentLName(),
            student.getStudentEmail(),
            student.getStudentId()
        );
    }

    @Override
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM Student WHERE StudentID = ?";
        jdbcTemplate.update(sql, studentId);
    }
    
}

