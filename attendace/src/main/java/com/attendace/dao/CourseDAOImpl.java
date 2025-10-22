package com.attendace.dao;

import com.attendace.model.Course;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private final JdbcTemplate jdbcTemplate;

    public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO Course (CourseCode, CourseName) VALUES (?, ?)";
        jdbcTemplate.update(sql,
            course.getCourseCode(),
            course.getCourseName()
        );
    }

    @Override
    public Course getCourseById(int courseId) { return null; }

    @Override
    public List<Course> getAllCourses() { return null; }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE Course SET CourseCode = ?, CourseName = ? WHERE CourseID = ?";
        jdbcTemplate.update(sql,
            course.getCourseCode(),  
            course.getCourseName(),  
            course.getCourseId()     
    );
    }

    @Override
    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM Course WHERE CourseID = ?";
        jdbcTemplate.update(sql, courseId);
    }

}
