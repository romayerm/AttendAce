package com.attendace.dao;

import com.attendace.model.Attendance;
import java.util.List;

public interface AttendanceDAO {
    void addAttendance(Attendance attendance);
    Attendance getAttendanceById(int sessionId, int studentId);
    List<Attendance> getAllAttendance();
    void updateAttendance(Attendance attendance);
    void deleteAttendance(int sessionId, int studentId);
}
