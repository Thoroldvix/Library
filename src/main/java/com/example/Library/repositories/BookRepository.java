package com.example.Library.repositories;

import com.example.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {




    @Query("from Book b where b.title like concat(:searchQuery, '%') ")
    List<Book> searchByQuery(@Param("searchQuery") String searchQuery);

    boolean existsByTitle(String title);

}
