package com.attendace.dao;

import com.attendace.model.Student;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student);
    Student getStudentByID(int studentId);
    List<Student> getAllStudents();
    void updateStudent(Student student);
    void deleteStudent(int studentId);
}
