package com.projectjy.projectjybackend.service;

import com.projectjy.projectjybackend.entity.Book;
import com.projectjy.projectjybackend.entity.Lecture;
import com.projectjy.projectjybackend.entity.LectureReview;
import com.projectjy.projectjybackend.entity.SaleBook;
import com.projectjy.projectjybackend.repository.*;
import com.projectjy.projectjybackend.security.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final SaleBookRepository saleBookRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public boolean saveSale(Book book, SaleBook saleBook, Lecture lecture, LectureReview review, String memberId) {
        if (book.getTitle() == null || book.getTitle().equals("")) {
            return false;
        }
        if (saleBook.getTitle() == null || saleBook.getTitle().equals("")) {
            return false;
        }
        if (saleBook.getConnect() == null || saleBook.getConnect().equals("")) {
            return false;
        }
        if (lecture.getTitle() == null || lecture.getTitle().equals("")) {
            return false;
        }

        try {
            Book savedBook;
            Optional<Member> savedMember = memberRepository.findByMemberId(memberId);

            if (!bookRepository.existsByCode(book.getCode())) savedBook = bookRepository.save(book);
            else savedBook = bookRepository.findByCode(book.getCode());

            saleBook.setBook(savedBook);
            saleBook.setLecture(lecture);
            saleBook.setDate(LocalDateTime.now());
            saleBook.setMember(savedMember.orElseThrow());
            if (review.getContent() != null && !review.getContent().equals("")) {
                review.setMember(savedMember.get());
                review.setDate(LocalDateTime.now());
                lectureReviewRepository.save(review);
            }
            saleBookRepository.save(saleBook);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long countBook() {
        return saleBookRepository.count();
    }

    public Page<SaleBook> getAllSaleBooksPageable(int page, int size) {
        Page<SaleBook> saleBooks = saleBookRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "date"));
        saleBooks.map(saleBook -> {
            Member member = saleBook.getMember();
            member.setPassword(null);
            member.setMemberId(null);
            member.setAuthority(null);
            saleBook.setMember(member);
            return saleBook;
        });
        return saleBooks;
    }

    public SaleBook getSaleBookById(String id) {
        SaleBook saleBook = saleBookRepository.findById(Long.valueOf(id)).orElseThrow();
        Member member = saleBook.getMember();
        member.setAuthority(null);
        member.setPassword(null);
        member.setMemberId(null);
        saleBook.setMember(member);
        return saleBook;
    }

    public Page<SaleBook> getSearchSaleBooksPageable(int page, int size, String keyword) {
        Page<SaleBook> saleBooks = saleBookRepository.findAllByBookTitleIsContainingOrLectureTitleIsContaining(keyword, keyword, PageRequest.of(page, size, Sort.Direction.DESC, "date"));
        saleBooks.map(saleBook -> {
            Member member = saleBook.getMember();
            member.setPassword(null);
            member.setMemberId(null);
            member.setAuthority(null);
            saleBook.setMember(member);
            return saleBook;
        });
        return saleBooks;
    }

    public List<SaleBook> getSaleHistory(String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow();
        return saleBookRepository.findAllByMemberOrderByDateDesc(member).stream().peek(saleBook -> {
            saleBook.getMember().setPassword(null);
            saleBook.getMember().setAuthority(null);
        }).collect(Collectors.toList());
    }

    public boolean soldOutChange(String id) {
        try {
            SaleBook saleBook = saleBookRepository.findById(Long.parseLong(id)).orElseThrow();
            saleBook.setSoldOut(!saleBook.isSoldOut());
            saleBookRepository.save(saleBook);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean viewIncrease(String id) {
        try {
            SaleBook saleBook = saleBookRepository.findById(Long.parseLong(id)).orElseThrow();
            saleBook.setView(saleBook.getView() + 1);
            saleBookRepository.save(saleBook);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean dateUpdate(String id) {
        try {
            SaleBook saleBook = saleBookRepository.findById(Long.parseLong(id)).orElseThrow();
            if (LocalDateTime.now().isAfter(saleBook.getDate().plusDays(3))) {
                saleBook.setDate(LocalDateTime.now());
                saleBookRepository.save(saleBook);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    public int countBookByMemberId(Long currentId) {
        Member member = memberRepository.findById(currentId).orElseThrow();
        return Math.toIntExact(saleBookRepository.countByMember(member));
    }

    public int getViews(Long currentId) {
        Member member = memberRepository.findById(currentId).orElseThrow();
        return saleBookRepository.findAllByMember(member).stream().mapToInt(SaleBook::getView).sum();
    }

}
