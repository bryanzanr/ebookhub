package com.herokuapp.ebookhub.book.usecases;

import com.herokuapp.ebookhub.book.dto.request.CreateRequest;
import com.herokuapp.ebookhub.book.entities.Book;
import com.herokuapp.ebookhub.book.entities.BookRepository;
// import com.herokuapp.ebookhub.book.usecases.CreateBookCmd;
// import com.herokuapp.ebookhub.book.usecases.CreateBookUseCase;
// import com.herokuapp.ebookhub.user.entities.User;
// import com.herokuapp.ebookhub.user.entities.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityExistsException;
// import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateBookUseCaseTest {

    // private final CreateBookCmd cmd = CreateBookCmd.valueOf("dummy", 1L);
    private final CreateBookCmd cmd = CreateBookCmd.valueOf("dummy");

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
    void givenEmptyRepository_shouldCreateUseCase() {
        useCase = new CreateBookUseCase(null);
    }

    @Test
    void givenNullRequest_whenAddBook_shouldCallSaveBook() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setTitle("asd");
        useCase.AddBook(createRequest);
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book actual = captor.getValue();

        assertThat(actual.getTitle()).isEqualTo(createRequest.getTitle());
    }

    @Test
    void givenException_whenAddBook_returnServerError() {
        CreateRequest createRequest = new CreateRequest();
        when(useCase.AddBook(createRequest))
            .thenThrow(new NullPointerException("error occured"));
        ResponseEntity<Map<String, Object>> result = useCase.AddBook(createRequest);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenNullRequest_whenCreateBook_shouldThrowException() {
        assertThatThrownBy(() -> useCase.createBook(null))
            .isInstanceOf(NullPointerException.class);
//            .hasMessage("cmd is marked non-null but is null");
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
    void givenRequest_whenGetBooks_shouldCallBookRepository() {
        when(bookRepository.findByCategory(anyString()))
            .thenReturn(Collections.singletonList(Book.builder().build()));
        useCase.GetBooks(0, 1, "asd");
        verify(bookRepository).findByCategory("asd");
    }

    @Test
    void givenEmptyCategory_whenGetBooks_shouldFindAll() {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("title").ascending());
        Page<Book> books = new PageImpl<Book>(Collections.singletonList(Book.builder().build()));
        when(bookRepository.findAll(pageable)).thenReturn(books);
        useCase.GetBooks(null, null, null);
        verify(bookRepository).findAll(pageable);
    }

    @Test
    void givenException_whenGetBooks_returnServerError() {
        when(useCase.GetBooks(null, null, "asd"))
            .thenThrow(new NullPointerException("error occured"));
        ResponseEntity<List<Object>> result = useCase.GetBooks(null, null, "asd");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenException_whenUpdateBook_returnServerError() {
        CreateRequest createRequest = new CreateRequest();
        when(useCase.UpdateBook(createRequest, 1L))
            .thenThrow(new NullPointerException("error occured"));
        ResponseEntity<Map<String, Object>> result = useCase.UpdateBook(createRequest, 1L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenException_whenDeleteBook_returnServerError() {
        when(useCase.DeleteBook(1L))
                .thenThrow(new NullPointerException("error occured"));
        ResponseEntity<Map<String, Object>> result = useCase.DeleteBook(1L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void prepareAndExecute() {
        stubBook();
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

    private void stubNotExistBook() {
        when(bookRepository.findByTitle(cmd.getBookTitle())).thenReturn(Optional.empty());
    }
}
