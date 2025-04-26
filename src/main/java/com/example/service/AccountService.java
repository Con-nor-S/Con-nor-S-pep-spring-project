package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Persists an account
     * @param acc account to persist
     * @return Account if valid account persisted, else null
     */
    public Account createAccount(Account acc){
        // Check if account already exists
        Optional<Account> existingAccount = accountRepository.findByUsername(acc.getUsername()); 
        if(existingAccount.isPresent()){
            return null;
        }
        // save new account
        return accountRepository.save(acc);
    }

    /**
     * Finds persisted account
     * @param acc account to check for
     * @return persisted account if one exists, else null
     */
    public Account findAccount(Account acc){
        // Query account info
        Optional<Account> existingAccount = 
            accountRepository.findByUsernameAndPassword(acc.getUsername(), acc.getPassword());
        // parse result
        if(existingAccount.isPresent())
            return existingAccount.get();
        return null;
    }

    /**
     * Gets all messages posted by given ID
     * @param id ID of account to find messages posted by
     * @return List of all messages posted by given ID
     */
    public List<Message> getAllMessages(Integer id){
        return messageRepository.findByPostedBy(id);
    }
}
