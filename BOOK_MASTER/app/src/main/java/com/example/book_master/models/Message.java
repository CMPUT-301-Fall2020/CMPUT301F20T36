package com.example.book_master.models;

import com.example.book_master.models.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String ISBN;
    private String status;
    private String longitude;
    private String latitude;

    public Message() {
        this.sender = "";
        this.receiver = "";
        this.ISBN = "";
        this.status = "";
        this.longitude = "";
        this.latitude = "";
    }

    public Message(String sender,
                   String receiver,
                   String ISBN,
                   String status,
                   String longitude,
                   String latitude) {
        this.sender = sender;
        this.receiver = receiver;
        this.ISBN = ISBN;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getSender() {return sender;}

    public void setSender(String sender) {this.sender = sender;}

    public String getReceiver() {return receiver;}

    public void setReceiver(String receiver) {this.receiver = receiver;}

    public String getISBN() {return ISBN;}

    public void setISBN(String ISBN) {this.ISBN = ISBN;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getLongitude() {return longitude;}

    public void setLongitude(String longitude) {this.longitude = longitude;}

    public String getLatitude() {return latitude;}

    public void setLatitude(String latitude) {this.receiver = latitude;}

    // identifier
    public int hashCode() { return sender.hashCode() * receiver.hashCode() * ISBN.hashCode(); }
}