package com.attendace.dao;

import com.attendace.model.Session;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDAOImpl implements SessionDAO {
    
    private final JdbcTemplate jdbcTemplate;

    public SessionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSession(Session session) {
        String sql = "INSERT INTO Session (SessionDate, CourseID) VALUES (?, ?)";
        jdbcTemplate.update(sql,
            session.getSessionDate(),
            session.getCourseId()
        );
    }

    @Override
    public Session getSessionById(String sessionDate, int courseId) { 
        String sql = "SELECT * FROM Session WHERE SessionDate = ? AND CourseID = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Session session = new Session();
            session.setSessionDate(LocalDate.parse(rs.getString("SessionDate")));
            session.setCourseId(rs.getInt("CourseID"));
            return session;
        }, sessionDate, courseId);
     }

    @Override
    public List<Session> getAllSessions() { 
        String sql = "SELECT * FROM Session";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Session session = new Session();
            session.setSessionDate(LocalDate.parse(rs.getString("SessionDate")));
            session.setCourseId(rs.getInt("CourseID"));
            return session;
        });
     }

    @Override
    public void updateSession(Session session, String oldSessionDate, int oldCourseId) {
        String sql = "UPDATE Session SET SessionDate = ?, CourseID = ? WHERE SessionDate = ? AND CourseID = ?";
        jdbcTemplate.update(sql,
            session.getSessionDate().toString(),
            session.getCourseId(),
            oldSessionDate,
            oldCourseId
        );
    }

    @Override
    public void deleteSession(String sessionDate, int courseId) {
        String sql = "DELETE FROM Session WHERE SessionDate = ? AND CourseID = ?";
        jdbcTemplate.update(sql, sessionDate, courseId);
    }

}
