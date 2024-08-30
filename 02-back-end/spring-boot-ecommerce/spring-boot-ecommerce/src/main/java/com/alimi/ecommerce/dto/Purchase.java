package com.alimi.ecommerce.dto;

import com.alimi.ecommerce.entity.Address;
import com.alimi.ecommerce.entity.Customer;
import com.alimi.ecommerce.entity.Order;
import com.alimi.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

}
