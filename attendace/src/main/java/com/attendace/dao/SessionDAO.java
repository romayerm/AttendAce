package com.attendace.dao;

import com.attendace.model.Session;
import java.util.List;

public interface SessionDAO {
    void addSession(Session session);
    Session getSessionById(String sessionDate, int courseId);
    List<Session> getAllSessions();
    void updateSession(Session session, String oldSessionDate, int oldCourseId);
    void deleteSession(String sessionDate, int courseId);
}
