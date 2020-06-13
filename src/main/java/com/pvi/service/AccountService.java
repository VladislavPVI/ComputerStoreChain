package com.pvi.service;

import com.pvi.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountRepo.findByLogin(s);
    }
}
