package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.entity.Book;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.entity.SaleBook;
import com.projectjy.projectjybackend.repository.BookRepository;
import com.projectjy.projectjybackend.repository.LectureReviewRepository;
import com.projectjy.projectjybackend.repository.MemberRepository;
import com.projectjy.projectjybackend.repository.SaleBookRepository;
import com.projectjy.projectjybackend.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final SaleBookRepository saleBookRepository;

    @Transactional
    public boolean saveSale(Book book, SaleBook saleBook, Lecture lecture, LectureReview review, String memberId) {
       if(book.getTitle() == null || book.getTitle().equals("")) {
           return false;
       }
       if(saleBook.getTitle() == null || saleBook.getTitle().equals("")) {
           return false;
       }
       if(saleBook.getConnect() == null || saleBook.getConnect().equals("")) {
           return false;
       }
       if(lecture.getTitle() == null || lecture.getTitle().equals("")) {
           return false;
       }

        try{
        Book savedBook;
        Member savedMember = memberRepository.findByMemberId(memberId).get();
        if (!bookRepository.existsByCode(book.getCode()))
            savedBook = bookRepository.save(book);
        else
            savedBook = bookRepository.findByCode(book.getCode());

        saleBook.setLectureId(lecture.getId());
        saleBook.setBookId(savedBook.getId());
        saleBook.setMemId(savedMember.getId());
        if(review.getContent() != null && !review.getContent().equals("")) {
            review.setMemId(savedMember.getId());
            lectureReviewRepository.save(review);
        }

        saleBookRepository.save(saleBook);
        lectureReviewRepository.save(review);
       return true;
       }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
