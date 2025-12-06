package com.attendace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendace.model.Course;

import jakarta.transaction.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
    Optional<Course> findByCourseCode(String courseCode);
    //Optional<Course> findByCourseName(String courseName);
    //findAll

    //create course

    @Transactional
    void deleteByCourseCode(String courseCode);
    //deleteAll
}
