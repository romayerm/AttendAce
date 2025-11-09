package com.attendace.model.dao;

import com.attendace.model.Attendance;
import java.util.List;

public interface AttendanceDAO {
    void addAttendance(Attendance attendance);
    Attendance getAttendanceById(String sessionDate, int courseId, int studentId);
    List<Attendance> getAllAttendance();
    void updateAttendance(Attendance attendance, String oldSessionDate, int oldCourseId, int oldStudentId);
    void deleteAttendance(String sessionDate, int courseId, int studentId);
}
