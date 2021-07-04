package com.herokuapp.ebookhub.book.usecases;

import com.herokuapp.ebookhub.book.entities.Book;
// import com.herokuapp.ebookhub.user.entities.User;
import lombok.Value;

@Value(staticConstructor = "valueOf")
public class CreateBookCmd {

    String bookTitle;

    // long userId;

    // public Book toEntity(User user) {
    public Book toEntity() {
        Book book = new Book();
        book.setTitle(bookTitle);
        // book.setUser(user);

        return book;
    }
}
