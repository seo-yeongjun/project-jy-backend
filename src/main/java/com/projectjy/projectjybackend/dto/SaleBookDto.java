package com.projectjy.projectjybackend.dto;

import com.projectjy.projectjybackend.entity.Book;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.entity.SaleBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleBookDto {
    private Book book;
    private Lecture lecture;
    private SaleBook saleBook;
    private LectureReview lectureReview;
    private String memberId;
}
