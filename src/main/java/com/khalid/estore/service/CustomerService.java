package com.khalid.estore.service;

import com.khalid.estore.dto.req.CustomerRequestDTO;
import com.khalid.estore.dto.resp.CustomerResponseDTO;
import com.khalid.estore.exception.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(Long id) throws ResourceNotFoundException;

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO) throws ResourceNotFoundException;

    void deleteCustomer(Long id) throws ResourceNotFoundException;
}
