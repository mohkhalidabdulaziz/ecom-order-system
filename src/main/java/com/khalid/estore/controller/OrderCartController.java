package com.khalid.estore.controller;

import com.khalid.estore.dto.resp.OrderCartResponseDTO;
import com.khalid.estore.dto.req.OrderCartItemRequestDTO;
import com.khalid.estore.exception.ResourceNotFoundException;
import com.khalid.estore.service.OrderCartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/order-cart")
public class OrderCartController {
    private final OrderCartService orderCartService;

    public OrderCartController(OrderCartService orderCartService) {
        this.orderCartService = orderCartService;
    }

    @GetMapping
    public ResponseEntity<OrderCartResponseDTO> getOrderCart(@PathVariable Long customerId) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderCartService.getOrderCart(customerId));
    }

    @PostMapping("/products/{productId}/quantity")
    public ResponseEntity<OrderCartResponseDTO> addToCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderCartService.addToCart(customerId, productId, quantity));
    }

    @PutMapping("/products/{productId}/quantity")
    public ResponseEntity<OrderCartResponseDTO> updateProductQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderCartService.updateProductQuantity(customerId, productId, quantity));
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderCartResponseDTO> placeOrder(
            @PathVariable Long customerId,
            @RequestBody @Valid List<OrderCartItemRequestDTO> items
    ) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCartService.placeOrder(customerId, items));
    }

}
