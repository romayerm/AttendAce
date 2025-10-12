package com.attendace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private Integer courseId;
    private String courseCode;
    private String courseName;

    public Course() {}

    public Course(Integer courseId, String courseCode, String courseName) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    //getters & setters
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //if course matches return true
        if (!(o instanceof Course)) return false; //if course is not on a course at all return false
        Course other = (Course) o;

        if (courseId != null && other.courseId != null) { //checks that primary key (courseId) exists for this course and other course
            return Objects.equals(courseId, other.courseId); 
        }

        return courseCode != null && courseName != null &&
           other.courseCode != null && other.courseName != null &&
           courseCode.trim().equalsIgnoreCase(other.courseCode.trim()) &&
           courseName.trim().equalsIgnoreCase(other.courseName.trim());
    }

    @Override
    public int hashCode() {
        if (courseId != null) {
            return courseId.hashCode();
        }
            return Objects.hash(
            courseCode != null ? courseCode.trim().toLowerCase() : null,
            courseName != null ? courseName.trim().toLowerCase() : null);
    }

    @Override
    public String toString() {
        return "Course:" +
               "\nCourse ID - " + courseId +
               ",\nCourse Code - " + courseCode + 
               ",\nCourse Name - " + courseName +
               ".";
    }
}
