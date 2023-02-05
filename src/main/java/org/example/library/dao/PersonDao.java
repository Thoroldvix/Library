package org.example.library.dao;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
 private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }


    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class),
                id);
    }
    public Optional<Person> checkIfNameExist (String name){
        return jdbcTemplate.query("SELECT * FROM person WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, year_of_birth) values (?, ?)", person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, year_of_birth=? WHERE id=?", updatedPerson.getName(),
                 updatedPerson.getYearOfBirth(), id);
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?",
               new BeanPropertyRowMapper<>(Book.class), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
}
