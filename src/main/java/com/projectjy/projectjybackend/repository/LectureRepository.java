package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.security.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAll();
    List<Lecture> findAllByDepartmentId(String departmentId);
    List<Lecture> findAllByTitleContaining(String title);
    Lecture getById(Long id);
}
