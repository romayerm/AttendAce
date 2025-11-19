package com.attendace.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendace.model.Session;
import com.attendace.model.Course;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findBySessionDate(LocalDate sessionDate);
    List<Session> findByCourse(Course course);
    Optional<Session> findBySessionDateAndCourse(LocalDate sessionDate, Course course);
    void deleteBySessionDateAndCourse(LocalDate sessionDate, Course course);
}
