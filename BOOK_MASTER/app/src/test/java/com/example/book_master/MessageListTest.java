package com.example.book_master;

import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for MessageList
 * Notice functionality related to Firebase could not be tested (it could not connect to cloud)
 */
public class MessageListTest {
    private Message mockMsg() {
        final String sender = "shrike";
        final String receiver = "shrike's friend";
        final String ISBN = "978-3-16-148410-0";
        final String status = "AVAILABLE";
        final String longitude = "2° 29' E";
        final String latitude = "47° 55' W";
        return new Message(sender, receiver, ISBN, status, longitude, latitude);
    }

    /**
     * ddMessage(Message msg), SearchISBN(), clearList()
     */
    @Test
    void testAddSearchISBNClear() {
        Message temp = mockMsg();
        String ISBN = temp.getISBN();

        MessageList.addMessage(temp);
        ArrayList<Message> qualified;
        qualified = MessageList.searchISBN(ISBN);
        assertTrue(qualified.size() != 0 && qualified.get(0).getISBN().equalsIgnoreCase(ISBN));
    }

    /**
     * searchSender(String sender), searchReceiver(String receiver)
     */
    @Test
    void testSearchSenderReceiver() {
        Message temp = mockMsg();
        String sender = temp.getSender();
        String receiver = temp.getReceiver();

        MessageList.addMessage(temp);
        ArrayList<Message> qualified;
        qualified = MessageList.searchSender(sender);
        assertTrue(qualified.size() != 0 && qualified.get(0).getSender().equalsIgnoreCase(sender));
        qualified = MessageList.searchReceiver(receiver);
        assertTrue(qualified.size() != 0 && qualified.get(0).getReceiver().equalsIgnoreCase(receiver));
    }
}
