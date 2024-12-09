package com.khalid.estore.repository;

import com.khalid.estore.entity.Customer;
import com.khalid.estore.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    OrderCart findByCustomer(Customer customer);

}
