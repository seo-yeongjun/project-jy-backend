package com.projectjy.projectjybackend.entity;

import com.projectjy.projectjybackend.security.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SaleBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "memId")
    private Member member;

    @OneToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    @OneToOne
    @JoinColumn(name = "bookId")
    Book book;

    private String title;
    private String content;
    private boolean soldOut;
    private String connect;
}
