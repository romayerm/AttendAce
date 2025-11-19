package com.attendace.model;

import jakarta.persistence.*;
//import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
    name = "Attendance"
    //uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "student_id"})
)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;
    
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus aStatus;

    public Attendance() {}

    public Attendance(Session session, Course course, Student student, AttendanceStatus aStatus) {
        this.session = session;
        this.course = course;
        this.student = student;
        this.aStatus = aStatus;
    }

    public Integer getAttendanceId() { return attendanceId; }
    public void setAttendanceId(Integer attendanceId) { this.attendanceId = attendanceId; }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public AttendanceStatus getAStatus() { return aStatus; }
    public void setAStatus(AttendanceStatus aStatus) { this.aStatus = aStatus; }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance other = (Attendance) o;

        if (attendanceId != null && other.attendanceId != null) {
            return Objects.equals(attendanceId, other.attendanceId);
        }

        return Objects.equals(session, other.session) &&
               Objects.equals(student, other.student);
    }

    @Override
    public int hashCode() {
        if (attendanceId != null) {
            return attendanceId.hashCode();
        }
        return Objects.hash(session, student);
    }

    @Override
    public String toString() {
        return "Attendance{" +
               "attendanceId=" + attendanceId +
               ", session=" + (session != null ? session.getSessionId() : null) +
               ", course=" + (course != null ? course.getCourseId() : null) +
               ", student=" + (student != null ? student.getStudentId() : null) +
               ", aStatus=" + aStatus +
               '}';
    }
}
