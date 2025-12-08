package com.attendace.model;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.List;


@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment for SQLite
    private Integer studentId;

    @Column(unique = true, nullable = false)
    private Integer emplid;

    @Column(nullable = false)
    private String studentFName;

    @Column(nullable = false)
    private String studentLName;

    @Column(unique = true, nullable = false)
    private String studentEmail;

    @ManyToMany
        @JoinTable(
            name = "EnrolledIn",
            joinColumns = @JoinColumn(name = "StudentID"),
            inverseJoinColumns = @JoinColumn(name = "CourseID")
        )
        private List<Course> courses;

    public Student() {}

    public Student(Integer emplid, String studentFName, String studentLName, String studentEmail) {
        this.emplid = emplid;
        this.studentFName = studentFName;
        this.studentLName = studentLName;
        this.studentEmail = studentEmail;
    }

    // Getters & Setters
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getEmplid() { return emplid; }
    public void setEmplid(Integer emplid) { this.emplid = emplid; }

    public String getStudentFName() { return studentFName; }
    public void setStudentFName(String studentFName) { this.studentFName = studentFName; }

    public String getStudentLName() { return studentLName; }
    public void setStudentLName(String studentLName) { this.studentLName = studentLName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
    
    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses) { this.courses = courses; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        if (studentId != null && other.studentId != null) {
            return Objects.equals(studentId, other.studentId);
        }
        return Objects.equals(emplid, other.emplid)
            && Objects.equals(studentEmail, other.studentEmail)
            && Objects.equals(studentFName, other.studentFName)
            && Objects.equals(studentLName, other.studentLName);
    }

    @Override
    public int hashCode() {
        if (studentId != null) {
            return studentId.hashCode();
        }
        return Objects.hash(
            emplid,
            studentEmail != null ? studentEmail.trim().toLowerCase() : null,
            studentFName != null ? studentFName.trim().toLowerCase() : null,
            studentLName != null ? studentLName.trim().toLowerCase() : null);
    }

    @Override
    public String toString() {
        return "Student{" +
               "studentId=" + studentId + '\'' +
               ", emplid=" + emplid + '\'' +
               ", firstName='" + studentFName + '\'' +
               ", lastName='" + studentLName + '\'' +
               ", email='" + studentEmail +
               '}';
    }
}
