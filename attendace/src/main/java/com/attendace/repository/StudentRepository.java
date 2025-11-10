/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.attendace.repository;

import java.util.Optional;

import com.attendace.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    //These methods are standard JPARepository methods. They are "magically" doing query's in the background
    // this only works because of careful naming: 
    // find_by_Emplid <- find is standard search word, by too and Emplid is our variable named capitalized
    Optional<Student> findByEmplid(Integer emplid);
    //Same here, so if you wanna add something else, just make sure you name and capitalize it correctly. 
    //For example I think findAll is another standard search word
    Student findByStudentEmail(String studentEmail);
}
