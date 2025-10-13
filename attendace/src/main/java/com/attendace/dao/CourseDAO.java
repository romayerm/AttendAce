package com.attendace.dao;

import com.attendace.model.Course;
import java.util.List;

public interface CourseDAO {
    void addCourse(Course course);
    Course getCourseById(int courseId);
    List<Course> getAllCourses();
    void updateCourse(Course course);
    void deleteCourse(int courseId);
}
