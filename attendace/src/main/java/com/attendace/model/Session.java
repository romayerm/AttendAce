package com.attendace.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer sessionId;

    @Column(nullable = false)
    private LocalDate sessionDate;

    //list of present and absent students by ID

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    public Session() {}

    public Session(LocalDate sessionDate, Course course) {
        this.sessionDate = sessionDate;
        this.course = course;
    }

    public Integer getSessionId() { return sessionId; }
    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session other = (Session) o;
        return Objects.equals(sessionId, other.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return "Session{" +
               "sessionId=" + sessionId + '\'' +
               ", sessionDate=" + sessionDate + '\'' +
               ", courseId=" + (course != null ? course.getCourseId() : null) +
               '}';
    }
}
