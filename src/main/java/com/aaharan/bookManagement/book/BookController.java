package com.aaharan.bookManagement.book;
import com.aaharan.bookManagement.auth.RegisterRequest;
import com.aaharan.bookManagement.school.School;
import com.aaharan.bookManagement.school.SchoolDto;
import com.aaharan.bookManagement.school.SchoolService;
import com.aaharan.bookManagement.utils.GenericResponse;
import com.aaharan.bookManagement.utils.SpecificationManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Books")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    @Autowired
    private BookService service;

    @Autowired
    private BookRepository repository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "This method add new book in the system")
    public GenericResponse<Book> AddNewBook(@RequestBody Book book) {
        if (repository.findByBookName(book.getBookName()).isPresent()) {
            return GenericResponse.<Book>builder()
                    .success(false)
                    .data(null)
                    .message("Book already Exist")
                    .build();
        }
        return GenericResponse.success(service.addNew(book));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "This method getting all the list of books available in the system")
    public GenericResponse<List<Book>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                   @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return GenericResponse.success(new ArrayList<>(service.getAll(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Update a particular book by id")
    public GenericResponse<Book> updateByUserId(@RequestBody Book book, @PathVariable int id) {
        Book updatedBook = service.updateById(book, id);
        return GenericResponse.success(updatedBook);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{bookId}")
    public GenericResponse<?> updateBookStatus(@PathVariable(name = "bookId") int bookId, @RequestParam Boolean isApproved) {
        String msg = "Book Approval Status Updated Successfully";
        Book bookObj=service.getById(bookId);
        bookObj.setIsActive(isApproved);
        service.updateById(bookObj, bookId);
        return GenericResponse.success(msg);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete School by id")
    public GenericResponse<String> deleteSchool(@PathVariable int id) {
        this.service.delete(id);
        return GenericResponse.success("School deleted successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Get a particular school by id")
    public GenericResponse<Book> getByID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByID(id));
    }



    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Search book by different criteria")
    public GenericResponse<List<Book>> getAllByCriteria(@RequestParam(value = "bookName", required = false) String bookName,
                                                          @RequestParam(value = "authorName", required = false) String authorName,
                                                          @RequestParam(value = "className", required = false) String className,
                                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Specification<Book> specification = SpecificationManager.getBookSpec(bookName, authorName, className);
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Book> schoolList = new ArrayList<>(service.getAllByCriteria(specification, pageable));
        return GenericResponse.success(schoolList);
    }

}
