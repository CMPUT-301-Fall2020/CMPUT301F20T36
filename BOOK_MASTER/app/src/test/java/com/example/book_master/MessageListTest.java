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
     * Test: addMessage(Message msg), searchISBN(), clearList()
     */
    @Test
    void testAddSearchISBNClear() {
        MessageList.clearList();

        Message temp = mockMsg();
        String ISBN = temp.getISBN();

        MessageList.addMessage(temp);
        ArrayList<Message> qualified;
        qualified = MessageList.searchISBN(ISBN);
        assertTrue(qualified.size() != 0 && qualified.get(0).getISBN().equalsIgnoreCase(ISBN));
        qualified = MessageList.searchISBN("garbage input");
        assertTrue(qualified.size() == 0);
        MessageList.clearList();
        qualified = MessageList.searchISBN(ISBN);
        assertTrue(qualified.size() == 0);
    }

    /**
     * Test: searchSender(String sender), searchReceiver(String receiver)
     */
    @Test
    void testSearchSenderReceiver() {
        MessageList.clearList();

        Message temp = mockMsg();
        String sender = temp.getSender();
        String receiver = temp.getReceiver();

        MessageList.addMessage(temp);
        ArrayList<Message> qualified;
        qualified = MessageList.searchSender(sender);
        assertTrue(qualified.size() != 0 && qualified.get(0).getSender().equalsIgnoreCase(sender));
        qualified = MessageList.searchSender("garbage input");
        assertTrue(qualified.size() == 0);

        qualified = MessageList.searchReceiver(receiver);
        assertTrue(qualified.size() != 0 && qualified.get(0).getReceiver().equalsIgnoreCase(receiver));
        qualified = MessageList.searchReceiver("garbage input");
        assertTrue(qualified.size() == 0);
    }

    /**
     * countMsgReceived(String receiver)
     */
    @Test
    void testCountMsgReceived() {
        MessageList.clearList();

        Message temp1 = mockMsg();
        temp1.setStatus("Borrowed");
        MessageList.addMessage(temp1);
        Message temp2 = mockMsg();
        MessageList.addMessage(temp2);

        int count1 = MessageList.countMsgReceived("shrike's friend");
        assertTrue(count1 == 2 );

        int count2 = MessageList.countMsgReceived("shrike");
        assertTrue(count2 == 0 );
        int count3 = MessageList.countMsgReceived("garbage input");
        assertTrue(count3 == 0 );
    }
}
