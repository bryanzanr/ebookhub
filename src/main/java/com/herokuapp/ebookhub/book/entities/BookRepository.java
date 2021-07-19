package com.herokuapp.ebookhub.book.entities;

import org.springframework.data.jpa.repository.JpaRepository;
// import com.herokuapp.ebookhub.book.entities.Book;

import java.util.Optional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    List<Book> findByCategory(String category);
    
}
