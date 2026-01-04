package com.example.libraryservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(nullable = false, length = 80)
    private String writer;

    @Column(nullable = false)
    private int availableCopies;

    protected BookEntity() {
    }

    public BookEntity(String name, String writer, int availableCopies) {
        this.name = name;
        this.writer = writer;
        this.availableCopies = availableCopies;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public String getWriter() {
        return writer;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void reduceStock() {
        if (availableCopies < 1) {
            throw new IllegalStateException("Stock épuisé");
        }
        availableCopies--;
    }
}
