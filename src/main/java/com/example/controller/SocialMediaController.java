package com.example.controller;

import com.example.entity.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController 
{
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    /*
     * createMessage: creates a message if its text is not blank, is not over 255 characters, and if
     * posted by has a real, existing user. If successful, return the created message and a status code
     * of 200. If it fails, return 400 as status code.
     * @param message: message to be recorded
     * @returns message with status code 200 if created, 400 if not.
     */
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message)
    {
        //Try to create the message
            //Try to find a user whose ID is postedBy
        Optional<Account> foundAccount = this.accountService.findAccountByID(message.getPostedBy());
            //Now check if message is empty or blank, if its longer than 255, and if a user was not found
        String text = message.getMessageText();
        if((text.isBlank() || text.isEmpty())
        || (text.length() > 255)
        || (!foundAccount.isPresent()))
        {
                //Either was true, return 400 status code
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            //It can be added, so add it
        Message addedMessage = this.messageService.addMessage(message);
            //And return the added message with status code 200
        return new ResponseEntity<>(addedMessage, HttpStatus.OK);
    }

    /*
     * getAllMessages: returns a list of all messages alongside 200 status code
     * @returns list of all messages. List could be empty. Returns status code 200 with that.
     */
    @GetMapping("/messages")
    public ResponseEntity getAllMessages()
    {
        //Get a list of all messages on Message table
            //Get all messages as list
        List<Message> messages = this.messageService.getAllMessages();
            //And return it with 200 status code
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /*
     * getMessageByMessageID: returns message by a given message ID. Always returns message, even if it is not found.
     * Also, sets status code to 200.
     * @param messageId: ID of message to be looked for
     * @returns status code 200 alongside a message, even if none is found 
     */
    @GetMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Message> getMessageByMessageID(@PathVariable Integer messageId)
    {
        //Return message given message id
            //Look for message given the ID
        Message message = this.messageService.getMessageByMessageID(messageId);
            //And return it alongside a 200 status
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /*
     * updateMessageByID: updates message by message ID. If message ID exists, message
     * text is not too long, and message text is not empty, message is updated
     * and this returns 200. Otherwise, returns 400.
     * @param messageID: ID of message possibly being replaced
     * @returns status code 400 if failed, 200 if success
     */
    @PatchMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> updateMessageByID(@PathVariable Integer messageId, @RequestBody Message message)
    {
        //Update the message associated with message ID
            //Update the message
        int rowsUpdated = this.messageService.updateMessageByID(messageId, message);
            //And check if we failed to update it
        if(rowsUpdated == 0)
        {
                //We failed, lets return 400
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
        //Succeeded in updating the message
            //Lets return 200
        return new ResponseEntity<Integer>(rowsUpdated, HttpStatus.OK);
    }

    /*
     * deleteMessage: delete a message by its ID
     * @param messageId: ID of message to be deleted
     * @returns number of messages deleted
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId)
    {
        //Try to delete message by message ID
            //Try to delete the message
        int numDeleted = this.messageService.deleteMessageByMessageID(messageId);
        if(numDeleted == 0)
            return new ResponseEntity<Integer>(HttpStatus.OK);
            //And return the number of rows deleted
        return new ResponseEntity<Integer>(numDeleted, HttpStatus.OK);
    }

    /*
     * createUser: If the username is not empty, password > 4 characters, and user does not exist, this returns 200 status code.
     * Otherwise, this returns a 409 status code.
     * @returns 200 if creation was successful, 409 if not
     */
    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody Account account)
    {
        //Try to add the account
            //Check if the account can't be added
        String username = account.getUsername();
        Account foundAccount = accountService.findAccountByUserName(username);
        if((username.isEmpty() || username.isBlank())
        || (account.getPassword().length() < 4)
        || (foundAccount != null))
        {
            //It can't, so return status code 409
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
            //It can, so try to add the user
        Account addedUser = accountService.addAccount(account);
            //And return the account with the status code 200
        return new ResponseEntity<>(addedUser, HttpStatus.OK);
    }

    /*
     * loginUser: returns 200 and account if username and password exist (if account exists), 
     * @param account: login information
     * @returns account if it is found with status code 200, 401 otherwise
     */
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Account account)
    {
        //Try to log in
            //Look for the account on our database
        Account foundAccount = this.accountService.findAccountByUsernamePassword(account);
            //And check if it was not found
        if(foundAccount == null)
        {
                //It was not, return unauthorized status code
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
            //It was, return the account with the OK status code
        return new ResponseEntity<>(foundAccount, HttpStatus.OK);
    }

    /*
     * getAllMessagesPostedBy: gets all messages posted by user. returns list and 200 status even if messages not found.
     * @param postedBy: user whose messages we want to get
     * @returns list of messages with status code
     */
    @GetMapping("/accounts/{postedBy}/messages")
    public ResponseEntity<List<Message>> getallMessagesPostedBy(@PathVariable int postedBy)
    {
        //Get all messages posted by user
            //Get messages from service
        List<Message> messages = this.messageService.getAllMessagesPostedBy(postedBy);
            //And return it with 200 status code
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }
}
