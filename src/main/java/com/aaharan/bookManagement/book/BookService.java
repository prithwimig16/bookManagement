package com.aaharan.bookManagement.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookService {

    Book addNew(Book obj);

    Book updateById(Book obj, int id);

    Book getByID(int id);

    List<Book> getAll(Pageable pageable);


    Book getById(int userId);

    void delete(int id);

    List<Book> getAllByCriteria(Specification<Book> specification, Pageable pageable);


}
