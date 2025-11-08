package com.attendace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
    private Integer studentId;
    private Integer emplid;
    private String studentFName;
    private String studentLName;
    private String studentEmail;

    public Student() {}

    public Student(Integer studentId, Integer emplid, String studentFName, String studentLName, String studentEmail) {
        this.studentId = studentId;
        this.emplid = emplid;
        this.studentFName = studentFName;
        this.studentLName = studentLName;
        this.studentEmail = studentEmail;
    }

    //getters & setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        if (studentId != null && other.studentId != null) {
            return Objects.equals(studentId, other.studentId);
        }
        return Objects.equals(emplid, other.emplid) &&
           studentEmail != null && other.studentEmail != null &&
           studentFName != null && other.studentFName != null &&
           studentLName != null && other.studentLName != null &&
           studentEmail.trim().equalsIgnoreCase(other.studentEmail.trim()) &&
           studentFName.trim().equalsIgnoreCase(other.studentFName.trim()) &&
           studentLName.trim().equalsIgnoreCase(other.studentLName.trim());
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
        return "Student: " +
               "\n ID - " + studentId +
               ",\n EMPLID - " + emplid +
               ",\n First Name - " + studentFName +
               ",\n Last Name - " + studentLName +
               ",\n Email - " + studentEmail +
               ".";
    }
}
