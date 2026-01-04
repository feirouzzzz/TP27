package com.example.libraryservice.web;

import com.example.libraryservice.web.LibraryController;
import com.example.libraryservice.domain.BookEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryController service;

    public LibraryController(LibraryController service) {
        this.service = service;
    }

    @GetMapping("/books")
    public List<BookEntity> getBooks() {
        return service.listBooks();
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public BookEntity create(@RequestBody BookEntity book) {
        return service.addBook(book);
    }

    @PostMapping("/books/{id}/borrow")
    public LibraryService.BorrowReceipt borrow(@PathVariable Long id) {
        return service.borrowBook(id);
    }
}
