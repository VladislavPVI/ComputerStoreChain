package com.pvi.repos;

import com.pvi.domain.Customer;
import com.pvi.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomer(Customer customer);
}

