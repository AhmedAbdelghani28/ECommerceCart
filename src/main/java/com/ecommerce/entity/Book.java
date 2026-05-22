package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
@DiscriminatorValue("BOOK")
public class Book extends Product {

    @Column(length = 50)
    private String author;

    @Column(length = 50)
    private String isbn;

    private int pages;

    @Column(length = 50)
    private String genre;

    @Column(name = "publish_year")
    private int publishYear;

    @Column(length = 50)
    private String language;

    public Book() {}

    public Book(String name, double price, String description,
                String author, String isbn, int pages,
                String genre, int publishYear, String language) {
        super(name, price, description);
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
        this.genre = genre;
        this.publishYear = publishYear;
        this.language = language;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}