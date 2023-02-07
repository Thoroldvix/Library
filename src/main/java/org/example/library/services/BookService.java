package org.example.library.services;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    private final PersonRepository personRepository;


    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;

        this.personRepository = personRepository;
    }

    public Book show(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    private List<Book> getPages(int pages, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(pages, booksPerPage)).getContent();
    }

    private List<Book> getSortedPages(int pages, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(pages, booksPerPage, Sort.by("year"))).getContent();
    }

    public Optional<Person> getBookOwner(int id) {
        return Optional.ofNullable(bookRepository.findById(id).getOwner());

    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = bookRepository.findById(id);
        book.setOwner(selectedPerson);
        bookRepository.save(book);
    }

    @Transactional
    public void release(int id) {
        Book book = bookRepository.findById(id);
        book.setOwner(null);
        bookRepository.save(book);
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
}
