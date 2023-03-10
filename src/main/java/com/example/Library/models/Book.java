package com.example.Library.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @NotNull(message = "Title should not be empty")
    private String title;

    @Column()
    @NotEmpty(message = "Author should not be empty")
    @NotNull(message = "Author should not be empty")
    private String author;

    @Transient
    private boolean isExpired;

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Column(name = "year")
    @Min(value = 1500, message = "Year should be greater than 1500")
    private int year;

    public Instant getRentTime() {
        return rentTime;
    }

    public void setRentTime(Instant rentTime) {
        this.rentTime = rentTime;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name ="rent_time")
    private Instant rentTime;
    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
