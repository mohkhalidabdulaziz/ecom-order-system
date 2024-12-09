package com.khalid.estore.repository;

import com.khalid.estore.entity.OrderCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartItemRepository extends JpaRepository<OrderCartItem, Long> {
}
