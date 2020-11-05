package com.example.book_master.models;

import com.example.book_master.models.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String content;

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public String getSender() {return sender;}

    public void setSender(String sender) {this.sender = sender;}

    public String getReceiver() {return receiver;}

    public void setReceiver(String receiver) {this.receiver = receiver;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    // identifier
    public int hashCode() { return sender.hashCode() * receiver.hashCode() * content.hashCode(); }
}

