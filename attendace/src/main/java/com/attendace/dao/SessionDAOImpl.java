package com.attendace.dao;

import com.attendace.model.Session;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDAOImpl {
    
    private final JdbcTemplate jdbcTemplate;

    public SessionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSession(Session session) {
        //
    }

    @Override
    public Session getSessionById(int sessionId) { return null; }

    @Override
    public List<Session> getAllSessions() { return null; }

    @Override
    public void updateSession(Session session) {
        //
    }

    @Override
    public void deleteSession(int sessionId) {
        //
    }

}
