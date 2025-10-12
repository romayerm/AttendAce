package com.attendace.dao;

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
        //
    }

    @Override
    public void getStudentByID(int studentId) {
        //
    }

    @Override
    public void getAllStudents() {
        //
    }

    @Override
    public void updateStudent(Student student) {
        //
    }

    @Override
    public void deleteStudent(int studentId) {
        //
    }
    
}

