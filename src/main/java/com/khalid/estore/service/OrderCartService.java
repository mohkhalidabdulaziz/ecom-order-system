package com.khalid.estore.service;


import com.khalid.estore.dto.CustomerDTO;
import com.khalid.estore.dto.OrderCartDTO;
import com.khalid.estore.dto.OrderCartItemDTO;
import com.khalid.estore.dto.ProductDTO;
import com.khalid.estore.entity.Customer;
import com.khalid.estore.entity.OrderCart;
import com.khalid.estore.entity.OrderCartItem;
import com.khalid.estore.entity.Product;
import com.khalid.estore.exception.ResourceNotFoundException;
import com.khalid.estore.repository.CustomerRepository;
import com.khalid.estore.repository.OrderCartRepository;
import com.khalid.estore.repository.ProductRepository;
import com.khalid.estore.util.OrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderCartService {

    @Autowired
    private OrderCartRepository orderCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderCartDTO addToCart(Long customerId, Long productId, int quantity) throws ResourceNotFoundException {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Find the cart for the customer
        OrderCart orderCart = orderCartRepository.findByCustomer(customer);
        if (orderCart == null) {
            orderCart = new OrderCart();
            orderCart.setCustomer(customer);
            orderCart.setStatus(OrderStatus.PENDING);
            orderCart.setItems(new ArrayList<>());
        }

        // Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check if the product is already in the cart
        Optional<OrderCartItem> existingItem = orderCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity if product already in cart
            OrderCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        } else {
            // Add new product to cart
            OrderCartItem item = new OrderCartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            orderCart.getItems().add(item);
        }

        // Recalculate total amount
        BigDecimal totalAmount = orderCart.getItems().stream()
                .map(OrderCartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderCart.setTotalAmount(totalAmount);
        orderCartRepository.save(orderCart);

        return convertToDTO(orderCart);
    }

    public OrderCartDTO getOrderCart(Long customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        OrderCart orderCart = orderCartRepository.findByCustomer(customer);
        if (orderCart == null) {
            return new OrderCartDTO();
        }
        return convertToDTO(orderCart);
    }


    public OrderCartDTO placeOrder(Long customerId, List<OrderCartItemDTO> items) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        OrderCart orderCart = new OrderCart();
        orderCart.setCustomer(customer);
        orderCart.setStatus(OrderStatus.PENDING);
        orderCart.setItems(new ArrayList<>());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderCartItemDTO itemDTO : items) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            totalAmount = totalAmount.add(price);

            OrderCartItem item = new OrderCartItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(price);

            orderCart.getItems().add(item);
        }

        orderCart.setTotalAmount(totalAmount);
        orderCart.setStatus(OrderStatus.PLACED);

        orderCartRepository.save(orderCart);
        return convertToDTO(orderCart);
    }

    // Reduce quantity of a product in the cart
    public OrderCartDTO updateProductQuantity(Long customerId, Long productId, int newQuantity) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        OrderCart orderCart = orderCartRepository.findByCustomer(customer);
        if (orderCart == null) {
            throw new ResourceNotFoundException("Order cart not found for the customer");
        }

        // Find the cart item corresponding to the product
        OrderCartItem itemToUpdate = orderCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in the cart"));

        // Update the quantity of the product
        if (newQuantity <= 0) {
            orderCart.getItems().remove(itemToUpdate);  // If quantity is 0 or less, remove the item
        } else {
            itemToUpdate.setQuantity(newQuantity);
            itemToUpdate.setPrice(itemToUpdate.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
        }

        // Recalculate the total amount
        BigDecimal totalAmount = orderCart.getItems().stream()
                .map(OrderCartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderCart.setTotalAmount(totalAmount);

        orderCartRepository.save(orderCart);

        return convertToDTO(orderCart);
    }

    private OrderCartDTO convertToDTO(OrderCart orderCart) {
        return modelMapper.map(orderCart, OrderCartDTO.class);
    }



}
