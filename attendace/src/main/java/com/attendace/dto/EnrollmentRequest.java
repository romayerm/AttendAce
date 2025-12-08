package com.attendace.dto;

import java.util.List;
import lombok.Data;

@Data
public class EnrollmentRequest {
    private List<Integer> studentIds;
    public List<Integer> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Integer> studentIds) { this.studentIds = studentIds; }
}
