package com.example.book_master.models;

import java.util.ArrayList;

public class MessageList {
    private static ArrayList<Message> messageList= new ArrayList<>();

    public static void addMessage(Message msg) {messageList.add(msg);}
    // deleting should be performed through DBHelper
    // call DBHelper.deleteMessageDoc(username, context) in one activity
    // and the change shall be automatically notified and saved in UserList

    public static ArrayList<Message> searchSender(String sender) {
        ArrayList<Message> qualifiedMessages = new ArrayList<>();
        for (Message iter : messageList) {
            if (iter.getSender() != null && iter.getSender().equalsIgnoreCase(sender)) {
                qualifiedMessages.add(iter);
            }
        }
        return qualifiedMessages;
    }

    public static ArrayList<Message> searchReceiver(String receiver) {
        ArrayList<Message> qualifiedMessages = new ArrayList<>();
        for (Message iter : messageList) {
            if (iter.getReceiver() != null && iter.getReceiver().equalsIgnoreCase(receiver)) {
                qualifiedMessages.add(iter);
            }
        }
        return qualifiedMessages;
    }

    public static ArrayList<Message> searchISBN(String ISBN) {
        ArrayList<Message> qualifiedMessages = new ArrayList<>();
        for (Message iter : messageList) {
            if (iter.getISBN() != null && iter.getISBN().equalsIgnoreCase(ISBN)) {
                qualifiedMessages.add(iter);
            }
        }
        return qualifiedMessages;
    }

    // clear the list, required by DBHelper
    public static void clearList(){ messageList.clear(); }
}
