package com.herokuapp.ebookhub.contact.usecases;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.herokuapp.ebookhub.CloudkitConfig;
import com.herokuapp.ebookhub.contact.dto.request.ContactRequest;
import com.herokuapp.ebookhub.contact.entities.ContactRepository;

import java.util.ArrayList;

public class LoginContactUseCase {

    private ContactRepository contactRepository;

    public LoginContactUseCase(CloudkitConfig cloudkitConfig) {
        this.contactRepository = new ContactRepository(cloudkitConfig);
    }

    public ResponseEntity<List<Object>> GetContacts() {
        List<Object> response = new ArrayList<>();
        try {
            response.add(this.contactRepository.findAll());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Object>> SaveContact(ContactRequest contactRequest) {
        List<Object> response = new ArrayList<>();
        try {
            response.add(this.contactRepository.save(
                contactRequest.getEmail(),
                contactRequest.getName()
            ));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}