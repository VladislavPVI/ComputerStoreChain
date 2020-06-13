package com.pvi.repos;

import com.pvi.domain.Account;
import com.pvi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByAccount(Account account);
}
