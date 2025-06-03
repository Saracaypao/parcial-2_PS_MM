package com.parcial02.demo.services;

import com.parcial02.demo.entities.dto.BookDto;
import com.parcial02.demo.entities.Book;
import com.parcial02.demo.exceptions.BookNotFoundException;
import com.parcial02.demo.exceptions.DuplicateIsbnException;
import com.parcial02.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private static BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public static List<Book> getAllBooks(String author, String language, String genre, Integer minPages, Integer maxPages) {
        if (author != null) return bookRepository.findByAuthor(author);
        if (language != null) return bookRepository.findByLanguage(language);
        if (genre != null) return bookRepository.findByGenre(genre);
        if (minPages != null && maxPages != null) return bookRepository.findByPagesBetween(minPages, maxPages);
        return bookRepository.findAll();
    }

    public static Book getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado por ISBN: " + isbn));
    }

    public Book create(BookDto dto) {
        if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new DuplicateIsbnException("ISBN ya registrado");
        }
        if (dto.getPublicationYear() > Year.now().getValue()) {
            throw new IllegalArgumentException("Año de publicación inválido");
        }
        Book book = new Book(null, dto.getTitle(), dto.getAuthor(), dto.getIsbn(), dto.getPublicationYear(),
                dto.getLanguage(), dto.getPages(), dto.getGenre());
        return bookRepository.save(book);
    }

    public void delete(String id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Libro no encontrado para eliminar");
        }
        bookRepository.deleteById(id);
    }

    public Book updateTitleOrLanguage(String id, String title, String language) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado para actualizar"));

        if (title != null && !title.matches(".*\\D.*")) {
            throw new IllegalArgumentException("El título no puede ser solo números");
        }

        if (title != null) book.setTitle(title);
        if (language != null) book.setLanguage(language);

        return bookRepository.save(book);
    }
}
