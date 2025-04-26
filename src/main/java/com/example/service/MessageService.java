package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Persists a message
     * @param msg message to persist
     * @return persisted message if successful, else null
     */
    public Message createMessage(Message msg){
        // Check message is valid
        Optional<Account> poster = accountRepository.findById(msg.getPostedBy());
        if( !poster.isPresent() || msg.getMessageText().isEmpty() 
            || msg.getMessageText().length() > 255)
            return null;
        // Create message
        return messageRepository.save(msg);
    }
}
