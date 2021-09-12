package com.herokuapp.ebookhub.user.usecases;

import com.herokuapp.ebookhub.user.dto.request.LoginRequest;
import com.herokuapp.ebookhub.user.dto.request.RegisterRequest;
import com.herokuapp.ebookhub.user.entities.User;
import com.herokuapp.ebookhub.user.entities.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterUserUseCaseTest {

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenEmptyRequest_whenLoginUser_shouldCallRepository() {
        LoginRequest loginRequest = new LoginRequest();
        registerUserUseCase.LoginUser(loginRequest);
        verify(userRepository).findAll();
    }

    @Test
    void givenException_whenLoginUser_shouldReturnError() {
        LoginRequest loginRequest = new LoginRequest();
        when(userRepository.findAll())
            .thenReturn(Collections.emptyList());
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.LoginUser(loginRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void givenEmptyUser_whenLoginUser_shouldReturnError() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(null, null, null, null));
        userList.add(null);
        when(userRepository.findAll())
            .thenReturn(userList);
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.LoginUser(new LoginRequest());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    void givenCorrectRequest_whenLoginUser_shouldReturnSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("asd");
        loginRequest.setPassword("asd");
        when(userRepository.findAll())
            .thenReturn(Collections.singletonList(new User(null, null, "asd", "asd")));
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.LoginUser(loginRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenCorrectRequest_whenRegisterUser_shouldReturnSuccess() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("asd");
        registerRequest.setPassword("asd");
        registerRequest.setEmail("asd");
        registerRequest.setRole("asd");
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.RegisterUser(registerRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenEmptyRequest_whenRegisterUser_shouldRaiseError() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(null);
        when(registerUserUseCase.RegisterUser(registerRequest))
            .thenThrow(new NullPointerException());
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.RegisterUser(registerRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenEmptyRequest_whenUpdateUser_shouldRaiseError() {
        RegisterRequest registerRequest = new RegisterRequest();
        when(registerUserUseCase.UpdateUser(registerRequest, 1L))
            .thenThrow(new NullPointerException());
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.UpdateUser(registerRequest, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenWrongRequest_whenDeleteUser_shouldRaiseError() {
        when(registerUserUseCase.DeleteUser(1L))
            .thenThrow(new NullPointerException());
        ResponseEntity<Map<String, Object>> response = registerUserUseCase.DeleteUser(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenWrongRequest_whenTestDatabase_shouldRaiseError() {
        when(registerUserUseCase.TestDatabase("asd"))
            .thenThrow(new NullPointerException());
        ResponseEntity<List<User>> response = registerUserUseCase.TestDatabase("asd");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void givenEmptyRequest_whenTestDatabase_shouldReturnSuccess() {
        when(userRepository.findAll())
            .thenReturn(Collections.singletonList(new User()));
        ResponseEntity<List<User>> response = registerUserUseCase.TestDatabase(null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}