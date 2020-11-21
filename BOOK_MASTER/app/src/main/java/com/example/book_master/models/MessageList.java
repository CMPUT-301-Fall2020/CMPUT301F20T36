package com.example.book_master.models;

import java.util.ArrayList;

/**
 * Static class receiving the asynchronous data updating from Firebase
 * Class and Activity should retrieve desired User info from here rather than invoking DBHelper
 */
public class MessageList {
    private static ArrayList<Message> messageList= new ArrayList<>();

    /**
     * adding & deleting should be performed through DBHelper
     * call
     *  DBHelper.setMessageDoc(msg.hashCode(), msg, context)
     *  DBHelper.deleteMessageDoc(msg.hashCode(), context)
     * in one activity, and the change shall be automatically notified and saved in MessageList
     */

    /**
     * Add one Message instance to messageList
     * @param msg Message instance to be added
     */
    public static void addMessage(Message msg) {
        if (!messageList.contains(msg)) {
            messageList.add(msg);
        }
    }

    /**
     * clear the list, required by DBHelper
     */
    public static void clearList() { messageList.clear(); }

    /**
     * Get all Messages that one User sent
     * @param sender username of the desired User
     * @return ArrayList<Message>
     */
    public static ArrayList<Message> searchSender(String sender) {
        ArrayList<Message> temp = new ArrayList<>();
        for (Message msg : messageList) {
            if (msg.getSender() != null && msg.getSender().equalsIgnoreCase(sender)) {
                temp.add(msg);
            }
        }
        return temp;
    }

    /**
     * Get all Messages that one User received
     * @param receiver username of the desired User
     * @return ArrayList<Message>
     */
    public static ArrayList<Message> searchReceiver(String receiver) {
        ArrayList<Message> temp = new ArrayList<>();
        for (Message msg : messageList) {
            if (msg.getReceiver() != null && msg.getReceiver().equalsIgnoreCase(receiver)) {
                temp.add(msg);
            }
        }
        return temp;
    }

    /**
     * Get all Messages that has the desired ISBN
     * @param ISBN desired ISBN
     * @return ArrayList<Message>
     */
    public static ArrayList<Message> searchISBN(String ISBN) {
        ArrayList<Message> temp = new ArrayList<>();
        for (Message msg : messageList) {
            if (msg.getISBN() != null && msg.getISBN().equalsIgnoreCase(ISBN)) {
                temp.add(msg);
            }
        }
        return temp;
    }
}
