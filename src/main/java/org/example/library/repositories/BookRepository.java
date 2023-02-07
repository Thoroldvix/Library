package org.example.library.repositories;

import org.example.library.models.Book;
import org.example.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {




    List<Book> findBooksByTitleLike(String searchQuery);

    boolean existsByTitle(String title);

}
