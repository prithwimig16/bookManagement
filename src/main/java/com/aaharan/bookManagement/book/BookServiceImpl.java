package com.aaharan.bookManagement.book;

import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private @Resource BookRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Book addNew(Book obj) {
        Book newBook = new Book();
        newBook.setBookName(obj.getBookName());
        newBook.setAuthorName(obj.getAuthorName());
        newBook.setClassName(obj.getClassName());
        newBook.setPrice(obj.getPrice());
        newBook.setIsActive(true);
        newBook.setCreatedAt(LocalDateTime.now());
        newBook.setUpdatedAt(LocalDateTime.now());
        return repository.save(newBook);
    }

    @Override
    public Book updateById(Book obj, int id) {
        return repository.findById(id)
                .map(it -> {
                    it.setAuthorName(obj.getAuthorName());
                    it.setBookName(obj.getBookName());
                    it.setClassName(obj.getClassName());
                    it.setIsActive(obj.getIsActive());
                    it.setUpdatedAt(LocalDateTime.now());
                    return repository.save(it);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }


    @Override
    public Book getByID(int id) {
        return this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }

    @Override
    public List<Book> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).getContent();

    }


    @Override
    public Book getById(int userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist"));
    }


    @Override
    public void delete(int id) {
        Book book = this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id doesn't exist"));
        this.repository.delete(book);
    }

    @Override
    public List<Book> getAllByCriteria(Specification<Book> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }



}

