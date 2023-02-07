package org.example.library.repositories;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findById(int id);




    boolean existsByTitle(String title);

}
