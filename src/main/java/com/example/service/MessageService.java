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

    /**
     * Returns all persisted messages
     * @return List of all messages
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * finds message of given ID
     * @param id ID of desired message
     * @return Message if exists, else null
     */
    public Message findMessageById(Integer id){
        Optional<Message> msg = messageRepository.findById(id);
        return msg.isPresent() ? msg.get() : null;
    }

    /**
     * Deletes message of given ID
     * @param id ID of message to delete
     * @return rows affected
     */
    public int deleteMessage(Integer id){
        // Check if message exists so we can return rows affected
        Optional<Message> msg = messageRepository.findById(id);
        // delete and return
        if(msg.isPresent()){
            messageRepository.deleteById(id);
            return 1;            
        }
        return 0;
    }

    /**
     * Updates message of given ID to given message
     * @param id ID of message to update
     * @param msg Message obj holding new message
     * @return rows affected
     */
    public int updateMessageById(Integer id, Message msg){
        // Check validity of update
        Optional<Message> oldMessage = messageRepository.findById(id);
        if(!oldMessage.isPresent() || msg.getMessageText().isEmpty() 
            || msg.getMessageText().length() > 255)
            return 0;
        // Make update
        msg.setMessageId(id);
        messageRepository.save(msg);
        return 1;
    }
}
