package com.attendace.attendace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
    private LocalDate sessionDate;
    private Integer courseId;

    public Session() {}

    public Session(LocalDate sessionDate, Integer courseId) {
        this.sessionDate = sessionDate;
        this.courseId = courseId;
    }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session other = (Session) o;
        return Objects.equals(sessionDate, other.sessionDate) && Objects.equals(courseId, other.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionDate, courseId);
    }

    @Override
    public String toString() {
        return "Session:" +
               "\nSession Date - " + sessionDate +
               ",\nCourse ID - " + courseId +
               ".";
    }
}
