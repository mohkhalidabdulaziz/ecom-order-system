package com.khalid.estore.controller;

import com.khalid.estore.dto.OrderCartDTO;
import com.khalid.estore.dto.OrderCartItemDTO;
import com.khalid.estore.exception.ResourceNotFoundException;
import com.khalid.estore.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/order-cart")
public class OrderCartController {

    @Autowired
    private OrderCartService orderCartService;

    @GetMapping
    public ResponseEntity<OrderCartDTO> getOrderCart(@PathVariable Long customerId) throws ResourceNotFoundException {
        OrderCartDTO orderCartDTO = orderCartService.getOrderCart(customerId);
        return ResponseEntity.ok(orderCartDTO);
    }

    @PostMapping("/products/{productId}/quantity")
    public ResponseEntity<OrderCartDTO> addToCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) throws ResourceNotFoundException {
        OrderCartDTO updatedCart = orderCartService.addToCart(customerId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/products/{productId}/quantity")
    public ResponseEntity<OrderCartDTO> updateProductQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity) throws ResourceNotFoundException {
        OrderCartDTO updatedCart = orderCartService.updateProductQuantity(customerId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderCartDTO> placeOrder(@PathVariable Long customerId, @RequestBody List<OrderCartItemDTO> items) throws ResourceNotFoundException {
        OrderCartDTO orderCartDTO = orderCartService.placeOrder(customerId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCartDTO);
    }

}
