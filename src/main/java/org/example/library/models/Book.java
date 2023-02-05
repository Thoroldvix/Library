package org.example.library.models;

import javax.validation.constraints.NotEmpty;

public class Book {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "Book title should not be empty")
    private String title;
    @NotEmpty(message = "Book author should not be empty")
    private String author;
    private int year;

    public String getTitle() {
        return title;
    }

    public Book() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
