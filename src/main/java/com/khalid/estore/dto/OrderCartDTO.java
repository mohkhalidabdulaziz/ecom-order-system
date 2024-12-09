package com.khalid.estore.dto;


import com.khalid.estore.util.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartDTO {

    private Long id;
    private Long customerId;
    private List<OrderCartItemDTO> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
