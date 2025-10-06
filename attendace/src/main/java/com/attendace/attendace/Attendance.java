package com.attendace.attendace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attendance {
    private LocalDate sessionDate;
    private Integer courseId;
    private Integer studentId;
    private AttendanceStatus aStatus;

    public Attendance() {}

    public Attendance(LocalDate sessionDate, Integer courseId, Integer studentId, AttendanceStatus aStatus) {
        this.sessionDate = sessionDate;
        this.courseId = courseId;
        this.studentId = studentId;
        this.aStatus = aStatus;
    }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public AttendanceStatus getAStatus() { return aStatus; }
    public void setAStatus(AttendanceStatus aStatus) { this.aStatus = aStatus; }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance other = (Attendance) o;
        return Objects.equals(sessionDate, other.sessionDate) &&
               Objects.equals(courseId, other.courseId) &&
               Objects.equals(studentId, other.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionDate, courseId, studentId);
    }

    @Override
    public String toString() {
        return "Attendance:" +
               "\nSession Date - " + sessionDate +
               ",\nCourse ID - " + courseId +
               ",\nStudent ID - " + studentId +
               ",\n Attendance Status - " + aStatus +
               ".";
    }
}
