package org.example.library.dao;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) values (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book show(int id) {
       return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?",
               new BeanPropertyRowMapper<>(Book.class), id);
    }
    public Optional<Book> checkIfTitleExists(String title) {
       return jdbcTemplate.query("SELECT FROM book WHERE title = ?",
               new Object[]{title}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book  JOIN person ON book.person_id = person_id WHERE book.id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

    }


    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id=? where id=?", selectedPerson.getId(), id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE id = ?", id);
    }
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? where id = ?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE from book where id = ?", id);
    }
}
