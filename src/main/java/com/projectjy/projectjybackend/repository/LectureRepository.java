package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAll();
    List<Lecture> findAllByTitleContaining(String title);
}
