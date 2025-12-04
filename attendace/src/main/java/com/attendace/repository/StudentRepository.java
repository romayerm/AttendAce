/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.attendace.repository;

import java.util.Optional;
import java.util.List;

import com.attendace.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    //These methods are standard JPARepository methods. They are "magically" doing query's in the background
    // this only works because of careful naming: 
    // find_by_Emplid <- find is standard search word, by too and Emplid is our variable named capitalized

    Optional<Student> findByEmplid(Integer emplid);
    //Optional<Student> findByStudentEmail(String studentEmail); <<< decided I don't need it for now
    Optional<Student> findByStudentLName(String studentLName);
    List<Student> findByCourses_CourseCode(String courseCode);
    //findAll is included by extending JPARepository

    //save (POST/create) is included by extending JPARepository

    void deleteByEmplid(Integer emplid);
    //deleteAll is included by extending JPARepository
    
}
