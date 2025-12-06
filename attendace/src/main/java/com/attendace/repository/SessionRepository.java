package com.attendace.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendace.model.Session;

import jakarta.transaction.Transactional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findBySessionDate(LocalDate sessionDate);
    List<Session> findByCourse_CourseCode(String courseCode);
    //findAll
    Optional<Session> findBySessionDateAndCourse_CourseCode(LocalDate sessionDate, String courseCode);

    //save (POST/create)

    @Transactional
    void deleteBySessionDateAndCourse_CourseCode(LocalDate sessionDate, String courseCode);
    //deleteAll
}
