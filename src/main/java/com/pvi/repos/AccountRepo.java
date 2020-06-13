package com.pvi.repos;

import com.pvi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByLogin(String login);
}
