package com.attendace.dao;

import com.attendace.model.Attendance;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDAOImpl implements AttendanceDAO {

    private final JdbcTemplate jdbcTemplate;

    public AttendanceDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addAttendance(Attendance attendance) {
        String sql = "INSERT INTO Attendance (SessionDate, CourseID, StudentID, AStatus) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            attendance.getSessionDate(),
            attendance.getCourseId(),
            attendance.getStudentId(),
            attendance.getAStatus()
        );
    }

    @Override
    public Attendance getAttendanceById(String sessionDate, int courseId, int studentId) { return null; }

    @Override
    public List<Attendance> getAllAttendance() { return null; }

    @Override
    public void updateAttendance(Attendance attendance, String oldSessionDate, int oldCourseId, int oldStudentId) {
        String sql = "UPDATE Attendance SET AStatus = ? WHERE SessionDate = ? AND CourseID = ? AND StudentID = ?";
        jdbcTemplate.update(sql,
            attendance.getAStatus(),   
            oldSessionDate,            
            oldCourseId,               
            oldStudentId               
        );
    }

    @Override
    public void deleteAttendance(String sessionDate, int courseId, int studentId) {
        String sql = "DELETE FROM Attendance WHERE SessionDate = ? AND CourseID = ? AND StudentID = ?";
        jdbcTemplate.update(sql, sessionDate, courseId, studentId);
    }

}
