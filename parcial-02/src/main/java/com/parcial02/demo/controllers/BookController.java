package com.parcial02.demo.controllers;

import com.parcial02.demo.entities.dto.BookDto;
import com.parcial02.demo.entities.Book;
import com.parcial02.demo.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks
            (@RequestParam(required = false) String author,
             @RequestParam(required = false) String language,
             @RequestParam(required = false) String genre,
             @RequestParam(required = false) Integer minPages,
             @RequestParam(required = false) Integer maxPages) {
        return ResponseEntity.ok(BookService.getAllBooks(author, language, genre, minPages, maxPages));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(BookService.getByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto dto) {
        try {
            bookService.create(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateTitleOrLanguage
            (@PathVariable String id,
             @RequestParam(required = false) String title,
             @RequestParam(required = false) String language) {
        return ResponseEntity.ok(bookService.updateTitleOrLanguage(id, title, language));
    }
}
