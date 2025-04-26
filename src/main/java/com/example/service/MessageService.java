package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService 
{
    @Autowired
    private MessageRepository messageRepository;

    /*
     * addMessage: adds message to Message table
     * @param message: message to be added
     * @returns new message if it was added
     */
    public Message addMessage(Message message)
    {
        //Try to add message
        return messageRepository.save(message);
    }

    /*
     * getAllMessages: return all messages in Message table
     * @returns all messages in table
     */
    public List<Message> getAllMessages()
    {
        //Get all messages
        return this.messageRepository.findAll();
    }

    /*
     * getMessageByMessageID: returns message given message ID
     * @param id: ID of message
     * @returns message using message ID
     */
    public Message getMessageByMessageID(int id)
    {
        //Find message by its ID
            //First, look for a message
        Optional<Message> foundMessage = this.messageRepository.findById(id);
            //Then, check if the message was found
        if(foundMessage.isPresent())
        {
            //It was, return it
            return foundMessage.get();
        }
            //It was not, return nothing
        return null;
    }
}
