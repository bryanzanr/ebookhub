package com.herokuapp.ebookhub.user.usecases;

import org.springframework.http.HttpStatus;

import com.herokuapp.ebookhub.user.entities.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.herokuapp.ebookhub.user.dto.request.LoginRequest;
import com.herokuapp.ebookhub.user.dto.request.RegisterRequest;

import java.util.List;
import com.herokuapp.ebookhub.user.entities.User;

public class RegisterUserUseCase {

	private UserRepository userRepository;

	public RegisterUserUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ResponseEntity<Map<String, Object>> LoginUser(LoginRequest loginRequest) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> users = userRepository.findAll();
		try {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUsername() == null || users.get(i).getPassword() == null) {
					continue;
				}
				if (users.get(i).getUsername().equalsIgnoreCase(loginRequest.getUsername()) && 
					users.get(i).getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
					response.put("status", 200);
					response.put("data", users.get(i));
					return new ResponseEntity<>(response, HttpStatus.OK);
				}	
			}
			response.put("data", "FAILED");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			response.put("error", e);
			response.put("reason", e.toString());
			response.put("data", users.get(0).getUsername());
			response.put("encyrpt", users.get(0).getPassword());
			response.put("username", loginRequest.getUsername());
			response.put("password", loginRequest.getPassword());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Map<String, Object>> RegisterUser(RegisterRequest registerRequest) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			userRepository.save(new User(
				registerRequest.getRole(),
				registerRequest.getEmail(),
				registerRequest.getPassword(),
				registerRequest.getUsername() 
				));
			response.put("status", 200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Map<String, Object>> UpdateUser(RegisterRequest registerRequest, Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			User user = new User(
				registerRequest.getRole(),
				registerRequest.getEmail(),
				registerRequest.getPassword(),
				registerRequest.getUsername() 
				);
			user.setId(id);
			userRepository.save(user);
			response.put("status", 200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Map<String, Object>> DeleteUser(Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			User user = new User();
			user.setId(id);
			userRepository.delete(user);
			response.put("status", 200);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("error", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<User>> TestDatabase(String email) {
		try {
			List<User> users = new ArrayList<User>();
  
			if (email == null) {
				userRepository.findAll().forEach(users::add);
			}else {
				userRepository.findByEmailContaining(email).forEach(users::add);
			}
	
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

}