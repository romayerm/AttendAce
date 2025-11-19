package com.attendace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendace.model.Attendance;
import com.attendace.model.Session;
import com.attendace.model.Course;
import com.attendace.model.Student;
import com.attendace.model.AttendanceStatus;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findBySession(Session session);
    List<Attendance> findByCourse(Course course);
    List<Attendance> findByStudent(Student student);

    Optional<Attendance> findBySessionAndStudent(Session session, Student student);

    List<Attendance> findBySessionAndAStatus(Session session, AttendanceStatus status);
    List<Attendance> findByCourseAndAStatus(Course course, AttendanceStatus status);
}
