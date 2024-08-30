package com.alimi.ecommerce.dao;

import com.alimi.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // SELECT * FROM customer c WHERE c.email = theEmail (behind the scenes)
    Customer findByEmail(String theEmail);
}
