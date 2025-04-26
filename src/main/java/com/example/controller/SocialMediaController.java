package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@ResponseBody
public class SocialMediaController {

    // Service objects
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    /**
     * endpoint handler to register new account
     * @param acc account to be registered
     * @return BAD_REQUEST if account invalid, CONFLICT if account exists, OK and new account if created
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account acc){
        // Check validity of input
        if(acc.getUsername().isEmpty() || acc.getPassword().length() < 4)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // Attempt creation
        Account addedAccount = accountService.createAccount(acc);
        // Create response
        return addedAccount == null ? new ResponseEntity<>(HttpStatus.CONFLICT) : 
            new ResponseEntity<>(addedAccount, HttpStatus.OK);
    }

    /**
     * Endpoint handler to login an account
     * @param acc account to log in
     * @return UNAUTHORIZED if incorrect credentials, OK and account if successful
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account acc){
        // Check for account
        Account loggedInAccount = accountService.findAccount(acc);
        // Create response
        return loggedInAccount == null ? new ResponseEntity<>(HttpStatus.UNAUTHORIZED) :
            new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
    }

    /**
     * Endpoint handler for create message
     * @param msg message to create
     * @return OK and message on success, else BAD_REQUEST
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message msg){
        // Attempt create
        Message createdMessage = messageService.createMessage(msg);
        // Create response
        return createdMessage == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
            new ResponseEntity<>(createdMessage, HttpStatus.OK);
    }

    /**
     * Endpoint handler to get messages
     * @return OK and all stored messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    /**
     * Endpoint handler to get message by ID
     * @param messageId ID of desired message
     * @return OK and message if present, else OK and empty body
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        // Check for message
        Message msg = messageService.findMessageById(messageId);
        // Create response
        return msg == null ? new ResponseEntity<>(HttpStatus.OK) : 
            new ResponseEntity<>(msg, HttpStatus.OK); 
    }
}
