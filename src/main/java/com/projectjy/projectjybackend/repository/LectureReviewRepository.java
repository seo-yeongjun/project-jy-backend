package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.LectureReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LectureReviewRepository extends JpaRepository<LectureReview,Long> {
    List<LectureReview> findAllByLectureId(Long lectureId);
}

