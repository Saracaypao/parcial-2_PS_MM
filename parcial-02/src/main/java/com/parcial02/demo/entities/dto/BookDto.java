package com.parcial02.demo.entities.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @Min(value = 1900)
    private Integer publicationYear;

    private String language;

    @Min(value = 11, message = "El libro teine que tener mas de 10 paginas")
    private Integer pages;

    @NotBlank
    private String genre;
}