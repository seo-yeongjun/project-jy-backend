package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.SaleBook;
import com.projectjy.projectjybackend.security.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface SaleBookRepository extends JpaRepository<SaleBook, Long>, PagingAndSortingRepository<SaleBook, Long> {
    List<SaleBook> findAllByMemberOrderByDateDesc(Member member);
    Page<SaleBook> findAllByBookTitleIsContainingOrLectureTitleIsContaining(String BookTitle, String LectureTitle, Pageable pageable);
    Long countByMember(Member member);

    List<SaleBook> findAllByMember(Member member);
}
