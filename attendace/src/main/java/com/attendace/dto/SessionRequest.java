package com.attendace.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SessionRequest {
    private LocalDate sessionDate;
    private String courseCode;
}
