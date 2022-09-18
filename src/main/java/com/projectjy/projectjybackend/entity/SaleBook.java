package com.projectjy.projectjybackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SaleBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memId;
    private Long lectureId;
    private Long bookId;

    private String title;
    private String content;
    private boolean soldOut;
    private String connect;
}
