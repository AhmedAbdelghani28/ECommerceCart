package com.ecommerce.dto;

import jakarta.validation.constraints.*;

public class BookRequest {

    @NotBlank @Size(max = 50)
    private String name;

    @Min(0)
    private double price;

    @Size(max = 100)
    private String description;

    @NotBlank @Size(max = 50)
    private String author;

    @NotBlank @Size(max = 50)
    private String isbn;

    @Min(1)
    private int pages;

    @Size(max = 50)
    private String genre;

    @Min(1)
    private int publishYear;

    @Size(max = 50)
    private String language;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

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
