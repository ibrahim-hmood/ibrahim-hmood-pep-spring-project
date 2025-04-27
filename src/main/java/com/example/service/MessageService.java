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

    /*
     * updateMessageByID: updates a message in database by given message ID
     * @param messageID: ID of message to be updated
     * @param message: updated message
     * @returns number of rows changed
     */
    public int updateMessageByID(int messageID, Message message)
    {
        //Update the message
            //Get the message text
        String text = message.getMessageText();
        Optional<Message> foundOptionalMessage = this.messageRepository.findById(messageID);
            //Now check if the ID exists, message is not too long, and if message is empty
        if((!foundOptionalMessage.isPresent())
            || (text.length() > 255)
            || (text.isEmpty()))
        {
            return 0;
        }
            //Conditions not met, save the message
        this.messageRepository.save(message);
        return 1;
    }

    /*
     * getAllMessagesPostedBy: gets all messages posted by user
     * @param postedBy: user whose messages we want to get
     * @returns list of messages
     */
    public List<Message> getAllMessagesPostedBy(int postedBy)
    {
        //Get a list of messages posted by user
            //Get that list
        return this.messageRepository.findAllByPostedBy(postedBy);
    }

    /*
     * deleteMessageByMessageID: deletes a message given its ID,
     * and returns number of rows deleted.
     * @param messageId: ID of message to be deleted
     * @returns number of rows deleted
     */
    public int deleteMessageByMessageID(Integer messageId)
    {
        //Try to delete the message by its ID
            //Try to delete the message and return number of rows deleted
        return this.messageRepository.deleteMessageByMessageID(messageId);
    }
}
