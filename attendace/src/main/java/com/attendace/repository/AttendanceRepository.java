package com.attendace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendace.model.Attendance;
import com.attendace.model.Session;
import com.attendace.model.Course;
import com.attendace.model.Student;

import jakarta.transaction.Transactional;

import com.attendace.model.AttendanceStatus;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findBySession(Session session);
    Optional<Attendance> findBySessionAndStudent(Session session, Student student);
    Optional<Attendance> findByCourseAndStudent(Course course, Student student);
    List<Attendance> findBySessionAndStatus(Session session, AttendanceStatus status);
    //findAll

    //create
    
    @Transactional
    void deleteBySession(Session session);
    @Transactional
    void deleteBySessionAndStudent(Session session, Student student);
    @Transactional
    void deleteByCourse(Course course);
    @Transactional
    void deleteByCourseAndStudent(Course course, Student student);
    //deleteAll
}
