package com.lctech.swm.service;

import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Users;
import com.lctech.swm.repository.AccountRepository;
import com.lctech.swm.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Find current user has login.
     *
     * @return User object
     */
    public Users findCurrentUser(){
        Users user = null;
        Account account =  this.findCurrentAccount();
        if(account != null && account.getUser() != null){
            user = usersRepository.findOne(account.getUser().getId());
        }
        return user;
    }

    /**
     * Find current account has logged in.
     *
     * @return Account object
     */
    public Account findCurrentAccount(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return accountRepository.findByUsername(username);

    }
}
