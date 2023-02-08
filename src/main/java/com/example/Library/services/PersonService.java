package com.example.Library.services;


import com.example.Library.models.Book;
import com.example.Library.models.Person;
import com.example.Library.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
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
            Instant currentTime = Instant.now();
            person.get().getBooks().stream()
                    .filter(book -> isRentExpired(currentTime, book.getRentTime()))
                    .forEach(book -> book.setExpired(true));
            return person.get().getBooks();
        } else
            return Collections.emptyList();
    }

    private boolean isRentExpired(Instant currentTime, Instant timeOfRent) {
        long days = TimeUnit.SECONDS.toDays(currentTime.getEpochSecond() - timeOfRent.getEpochSecond());
        return days > 10;
    }
}
