package com.khalid.estore.service;


import com.khalid.estore.dto.CustomerDTO;
import com.khalid.estore.entity.Customer;
import com.khalid.estore.exception.ResourceNotFoundException;
import com.khalid.estore.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO createCustomer(Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);
        return convertToDTO(createdCustomer);
    }

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get customer by ID
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Update customer
    public CustomerDTO updateCustomer(Long id, Customer customerDetails) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setName(customerDetails.getName());
        customer.setAddress(customerDetails.getAddress());
        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    private CustomerDTO convertToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

}
