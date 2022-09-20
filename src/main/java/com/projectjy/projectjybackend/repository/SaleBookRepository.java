package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.SaleBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface SaleBookRepository extends JpaRepository<SaleBook, Long>, PagingAndSortingRepository<SaleBook, Long> {
}
