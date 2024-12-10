package com.khalid.estore.controller;

import com.khalid.estore.dto.req.CustomerRequestDTO;
import com.khalid.estore.dto.resp.CustomerResponseDTO;
import com.khalid.estore.exception.ResourceNotFoundException;
import com.khalid.estore.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(201).body(createdCustomer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) throws ResourceNotFoundException {
        CustomerResponseDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) throws ResourceNotFoundException {
        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(id, customerRequestDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
