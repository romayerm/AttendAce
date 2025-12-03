package com.attendace.dto;

import lombok.Data;
import java.time.LocalDate;
import com.attendace.model.AttendanceStatus;

@Data
public class AttendanceRequest {
    private LocalDate sessionDate;
    private String courseCode;
    private Integer emplid;
    private AttendanceStatus aStatus;
}
