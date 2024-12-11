package com.khalid.estore.service;

import com.khalid.estore.dto.resp.OrderCartResponseDTO;
import com.khalid.estore.dto.req.OrderCartItemRequestDTO;

import com.khalid.estore.exception.ResourceNotFoundException;

import java.util.List;


public interface OrderCartService {

    OrderCartResponseDTO getOrderCart(Long customerId) throws ResourceNotFoundException;

    OrderCartResponseDTO addToCart(Long customerId, Long productId, int quantity) throws ResourceNotFoundException;

    OrderCartResponseDTO updateProductQuantity(Long customerId, Long productId, int quantity) throws ResourceNotFoundException;

    OrderCartResponseDTO placeOrder(Long customerId, List<OrderCartItemRequestDTO> items) throws ResourceNotFoundException;

}
