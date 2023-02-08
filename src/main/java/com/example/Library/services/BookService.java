package com.example.Library.services;


import com.example.Library.models.Book;
import com.example.Library.models.Person;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;




    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;


    }

    public Book show(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        book.setRentTime(Instant.now());
        bookRepository.save(book);
    }

    public List<Book> index(Optional<Integer> pages, Optional<Integer> booksPerPage, boolean sortByYear) {
        if (pages.isPresent() && booksPerPage.isPresent() && sortByYear) {
            return getSortedPages(pages.get(), booksPerPage.get());
        }
        else if (pages.isPresent() && booksPerPage.isPresent()) {
            return getPages(pages.get(), booksPerPage.get());
        }
        else if (sortByYear) {
            return bookRepository.findAll(Sort.by( "year"));
        }
        return bookRepository.findAll();
    }

    private List<Book> getPages(int pages, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(pages, booksPerPage)).getContent();
    }

    private List<Book> getSortedPages(int pages, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(pages, booksPerPage, Sort.by("year"))).getContent();
    }

    public Optional<Person> getBookOwner(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(Book::getOwner);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            book.get().setOwner(selectedPerson);
            book.get().setRentTime(Instant.now());
            bookRepository.save(book.get());
        }
    }

    @Transactional
    public void release(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            book.get().setOwner(null);
            book.get().setRentTime(null);
            bookRepository.save(book.get());
        }
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);

    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public boolean checkIfTitleExists(String title) {
        return bookRepository.existsByTitle(title);
    }

    public List<Book> search(Optional<String> searchQuery) {
        return searchQuery.map(bookRepository::searchByQuery).orElse(null);
    }
}
