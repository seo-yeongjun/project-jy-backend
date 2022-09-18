package com.projectjy.projectjybackend.repository;

import com.projectjy.projectjybackend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
    boolean existsByCode(String code);

    Book findByCode(String code);
}
