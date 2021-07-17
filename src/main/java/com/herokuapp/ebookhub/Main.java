/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.herokuapp.ebookhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.herokuapp.ebookhub.user.dto.request.LoginRequest;
import com.herokuapp.ebookhub.user.dto.request.RegisterRequest;
import com.herokuapp.ebookhub.user.entities.User;
import com.herokuapp.ebookhub.user.usecases.RegisterUserUseCase;
import com.herokuapp.ebookhub.user.entities.UserRepository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@SpringBootApplication
public class Main {

	private UserRepository userRepository;

	@Autowired
	public Main(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public static void main (String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> Login(
		@RequestBody(required = false) LoginRequest loginRequest) {
			return new RegisterUserUseCase(this.userRepository).LoginUser(loginRequest);
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> Register(
		@RequestBody(required = false) RegisterRequest registerRequest) {
			return new RegisterUserUseCase(this.userRepository).RegisterUser(registerRequest);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<Map<String, Object>> UpdateUser(
	@PathVariable("id") long id,
	@RequestBody(required = false) RegisterRequest registerRequest) {
		return new RegisterUserUseCase(this.userRepository).UpdateUser(registerRequest, id);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<Map<String, Object>> DeleteUser(
	@PathVariable("id") long id) {
		return new RegisterUserUseCase(this.userRepository).DeleteUser(id);
	}
	
	@GetMapping("/db")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String email) {
		return new RegisterUserUseCase(this.userRepository).TestDatabase(email);
	}

}