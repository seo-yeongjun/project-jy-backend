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
    private Long lectureId;
    private String content;
    private LocalDateTime date;
}
