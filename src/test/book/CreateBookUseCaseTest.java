package com.herokuapp.ebookhub.book.usecases;

import com.herokuapp.ebookhub.book.entities.Book;
import com.herokuapp.ebookhub.book.entities.BookRepository;
// import com.herokuapp.ebookhub.user.entities.User;
// import com.herokuapp.ebookhub.user.entities.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateBookUseCaseTest {

    private final CreateBookCmd cmd = CreateBookCmd.valueOf("dummy", 1L);

    @InjectMocks
    private CreateBookUseCase useCase;

    // @Mock
    // private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    // private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenNullRequest_whenCreateBook_shouldThrowException() {
        assertThatThrownBy(() -> useCase.createBook(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("cmd is marked non-null but is null");
    }

    // @Test
    // void givenRequest_whenCreateBook_shouldCallUserRepository() {
    //     prepareAndExecute();

    //     verify(userRepository).findById(1L);
    // }

    // @Test
    // void givenNonExistingUser_whenCreateBook_shouldThrowException() {
    //     when(userRepository.findById(cmd.getUserId())).thenReturn(Optional.empty());

    //     assertThatThrownBy(() -> useCase.createBook(cmd))
    //         .isInstanceOf(EntityNotFoundException.class)
    //         .hasMessage("User with Id 1 is not found");
    // }

    @Test
    void givenRequest_whenCreateBook_shouldCallBookRepository() {
        prepareAndExecute();

        verify(bookRepository).findByTitle(cmd.getBookTitle());
    }

    @Test
    void givenExistingBook_whenCreateBook_shouldThrowException() {
        stubBook();
        // stubUser();

        assertThatThrownBy(() -> useCase.createBook(cmd))
            .isInstanceOf(EntityExistsException.class)
            .hasMessage("Book dummy is already exist");
    }

    @Test
    void givenRequest_whenCreateBook_shouldCallSaveBook() {
        prepareAndExecute();

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book actual = captor.getValue();

        assertThat(actual.getTitle()).isEqualTo(cmd.getBookTitle());
        // assertThat(actual.getUser()).isEqualTo(user);
    }

    private void prepareAndExecute() {
        stubUser();
        stubNotExistBook();

        useCase.createBook(cmd);
    }

    // private void stubUser() {
    //     user = User.builder().name("user").build();
    //     when(userRepository.findById(cmd.getUserId())).thenReturn(Optional.of(user));
    // }

    private void stubBook() {
        Book injected = Book.builder().build();
        when(bookRepository.findByTitle(cmd.getBookTitle())).thenReturn(Optional.of(injected));
    }

    private void stubNotExistCompany() {
        when(bookRepository.findByTitle(cmd.getBookTitle())).thenReturn(Optional.empty());
    }
}
