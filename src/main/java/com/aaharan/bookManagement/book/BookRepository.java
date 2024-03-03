package com.aaharan.bookManagement.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findById(int id);
    Optional<Book> findByBookName(String name);


    List<Book> findAll(Specification<Book> specification, Pageable pageable);

}
