package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>
{
    /*
     * findAllByPostedBy: finds all messages posted by user
     * @param postedBy: ID of user whose messages we want to get
     * @returns list of messages
     */
    List<Message> findAllByPostedBy(int postedBy);

    /*
     * deleteMessageByMessageId: removes a message by its id, returns number of rows changed
     * @param messageId: ID of message to be deleted
     * @returns number of rows deleted
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId = :messageId")
    public int deleteMessageByMessageID(@Param("messageId") Integer messageId);
}

