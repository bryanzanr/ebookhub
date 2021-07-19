package com.herokuapp.ebookhub.book.usecases;

import com.herokuapp.ebookhub.book.entities.BookRepository;
import com.herokuapp.ebookhub.book.dto.request.CreateRequest;
import com.herokuapp.ebookhub.book.entities.Book;
// import com.herokuapp.ebookhub.user.entities.User;
// import com.herokuapp.ebookhub.user.entities.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
// import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class CreateBookUseCase {

    // private final UserRepository userRepository;

    private BookRepository bookRepository;

    public CreateBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createBook(@NonNull CreateBookCmd cmd) {
        // User user = getUser(cmd);

        bookRepository.findByTitle(cmd.getBookTitle()).ifPresent(book -> {
            throw new EntityExistsException(String.format("Book %s is already exist", cmd.getBookTitle()));
        });

        // bookRepository.save(cmd.toEntity(user));
        bookRepository.save(cmd.toEntity());
    }

    public ResponseEntity<List<Object>> GetBooks(Integer page, Integer pageSize, String category) {
		List<Object> response = new ArrayList<Object>();
        Pageable pageable;
        if (page != null && pageSize != null) {
            pageable = PageRequest.of(page, pageSize, Sort.by("title").ascending());
        }else {
            pageable = PageRequest.of(0, 25, Sort.by("title").ascending());
        }
        List<Book> books = new ArrayList<Book>();
        if (category != null && category != "") {
            books = bookRepository.findByCategory(category);
        }else {
            books = bookRepository.findAll(pageable).getContent();
        }
		try {
			for (int i = 0; i < books.size(); i++) {
                Map<String, Object> book = new HashMap<String, Object>();
                book.put("data", books.get(i));	
                response.add(book);
			}
            return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    public ResponseEntity<Map<String, Object>> AddBook(CreateRequest createRequest) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			bookRepository.save(new Book(
				createRequest.getImgPath(),
				createRequest.getTitle(),
				createRequest.getAuthor(),
				createRequest.getPublisher(),
                createRequest.getDescription(),
                createRequest.getStock(),
                createRequest.getCategory(),
                new Date()
				));
			response.put("status", 200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e);
            response.put("request", createRequest);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    public ResponseEntity<Map<String, Object>> UpdateBook(CreateRequest createRequest, Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Book book = new Book(
				createRequest.getImgPath(),
				createRequest.getTitle(),
				createRequest.getAuthor(),
				createRequest.getPublisher(),
                createRequest.getDescription(),
                createRequest.getStock(),
                createRequest.getCategory(),
                new Date()
				);
			book.setBookId(id);
			bookRepository.save(book);
			response.put("status", 200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    public ResponseEntity<Map<String, Object>> DeleteBook(Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Book book = new Book();
			book.setBookId(id);
			bookRepository.delete(book);
			response.put("status", 200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    // private User getUser(CreateBookCmd cmd) {
    //     return userRepository.findById(cmd.getUserId())
    //         .orElseThrow(() -> new EntityNotFoundException(
    //             String.format("User with Id %s is not found", cmd.getUserId()))
    //         );
    // }
}
