package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository questionRepository;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getQuestions(@PathVariable Long customerId) {
    	Customer c=  questionRepository.findById(customerId)
    			.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + customerId));
         return  ResponseEntity.ok().body(c);
    }


    @PostMapping("/customer")
    public ResponseEntity<Customer> createQuestion(@Valid @RequestBody Customer customer) {
    	if(customer.getPassword().length()<8||customer.getPassword().length()>10) {
    		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
        return ResponseEntity.status(HttpStatus.CREATED).body(questionRepository.save(customer));
    }

       
}
