package com.herokuapp.ebookhub.book.usecases;

import com.herokuapp.ebookhub.book.entities.BookRepository;
// import com.herokuapp.ebookhub.user.entities.User;
// import com.herokuapp.ebookhub.user.entities.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CreateBookUseCase {

    // private final UserRepository userRepository;

    private final BookRepository bookRepository;

    public void createBook(@NonNull CreateBookCmd cmd) {
        // User user = getUser(cmd);

        bookRepository.findByTitle(cmd.getBookTitle()).ifPresent(book -> {
            throw new EntityExistsException(String.format("Book %s is already exist", cmd.getBookTitle()));
        });

        // bookRepository.save(cmd.toEntity(user));
        bookRepository.save(cmd.toEntity());
    }

    // private User getUser(CreateBookCmd cmd) {
    //     return userRepository.findById(cmd.getUserId())
    //         .orElseThrow(() -> new EntityNotFoundException(
    //             String.format("User with Id %s is not found", cmd.getUserId()))
    //         );
    // }
}
