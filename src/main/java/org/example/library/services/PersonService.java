package org.example.library.services;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.example.library.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> index() {
        return personRepository.findAll();
    }

    public boolean checkIfNameExist(String name) {
        return personRepository.existsByName(name);
    }

    public Person show(int id) {
       return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            Date currentTime = new Date();
        List<Book> books = person.get().getBooks();
        for (Book book : books) {
            if (isRentExpired(currentTime, book.getRentTime()))
                book.setExpired(true);
        }
        return person.get().getBooks();
        } else
            return Collections.emptyList();
    }
    private boolean isRentExpired(Date currentTime, Date timeOfRent) {
       long days = TimeUnit.MILLISECONDS.toDays(currentTime.getTime() - timeOfRent.getTime());
       return  days > 10;
    }
}
