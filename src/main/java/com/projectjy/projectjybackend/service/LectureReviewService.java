package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.repository.LectureReviewRepository;
import com.projectjy.projectjybackend.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureReviewService {
    private final LectureReviewRepository lectureReviewRepository;

    public List<LectureReview> getLectureReviewsByLectureId(Long lectureId) {
        return lectureReviewRepository.findAllByLectureId(lectureId).stream().peek(lectureReview -> {
            Member member = lectureReview.getMember();
            member.setPassword(null);
            member.setMemberId(null);
            member.setAuthority(null);
            lectureReview.setMember(member);
        }).collect(Collectors.toList());
    }

    public List<LectureReview> getAllLectureReviews() {
        return lectureReviewRepository.findAll().stream().peek(lectureReview -> {
            Member member = lectureReview.getMember();
            member.setPassword(null);
            member.setMemberId(null);
            member.setAuthority(null);
            lectureReview.setMember(member);
        }).collect(Collectors.toList());
    }
}
