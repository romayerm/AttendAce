package com.attendace.dao;

import com.attendace.model.Course;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAOImpl {

    private final JdbcTemplate jdbcTemplate;

    public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCourse(Course course) {
        //
    }

    @Override
    public Course getCourseById(int courseId) { return null; }

    @Override
    public List<Course> getAllCourses() { return null; }

    @Override
    public void updateCourse(Course course) {
        //
    }

    @Override
    public void deleteCourse(int courseId) {
        //
    }

}
