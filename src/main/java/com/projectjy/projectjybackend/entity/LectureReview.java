package com.projectjy.projectjybackend.entity;

import com.projectjy.projectjybackend.security.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LectureReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "memId")
    private Member member;
    @OneToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;
    private String content;
    private LocalDateTime date;
}
