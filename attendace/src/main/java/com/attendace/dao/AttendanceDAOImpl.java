package com.attendace.dao;

import com.attendace.model.Attendance;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDAOImpl {

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
    public Attendance getAttendanceById(int sessionId, int studentId) { return null; }

    @Override
    public List<Attendance> getAllAttendance() { return null; }

    @Override
    public void updateAttendance(Attendance attendance) {
        //
    }

    @Override
    public void deleteAttendance(int sessionId, int studentId) {
        //
    }

}
