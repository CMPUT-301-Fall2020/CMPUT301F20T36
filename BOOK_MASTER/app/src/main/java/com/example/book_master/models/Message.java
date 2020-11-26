package com.example.book_master.models;

import java.io.Serializable;

/**
 * Store one specific book info
 */
public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String ISBN;
    private String status;
    private String longitude;
    private String latitude;

    /**
     * Empty constructor required by Firebase
     */
    public Message() {
        this.sender = "";
        this.receiver = "";
        this.ISBN = "";
        this.status = "";
        this.longitude = "";
        this.latitude = "";
    }

    /**
     * Constructor
     * @param sender message sender
     * @param receiver message receiver
     * @param ISBN related Book ISBN
     * @param status related Book status
     * @param longitude longitude of the sender location
     * @param latitude latitude of the receiver location
     */
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

    /**
     * @return message sender
     */
    public String getSender() {return sender;}

    /**
     * @param sender message sender
     */
    public void setSender(String sender) {this.sender = sender;}

    /**
     * @return message receiver
     */
    public String getReceiver() {return receiver;}

    /**
     * @param receiver message receiver
     */
    public void setReceiver(String receiver) {this.receiver = receiver;}

    /**
     * @return related Book ISBN
     */
    public String getISBN() {return ISBN;}

    /**
     * @param ISBN related Book ISBN
     */
    public void setISBN(String ISBN) {this.ISBN = ISBN;}

    /**
     * @return related Book status
     */
    public String getStatus() {return status;}

    /**
     * @param status related Book status
     */
    public void setStatus(String status) {this.status = status;}

    /**
     * @return longitude of the sender location
     */
    public String getLongitude() {return longitude;}

    /**
     * @param longitude longitude of the sender location
     */
    public void setLongitude(String longitude) {this.longitude = longitude;}

    /**
     * @return latitude of the receiver location
     */
    public String getLatitude() {return latitude;}

    /**
     * @param latitude latitude of the receiver location
     */
    public void setLatitude(String latitude) {this.latitude = latitude;}

    /**
     * Message identifier
     * @return hash code for the Message
     */
    public int hashCode() { return sender.hashCode() * receiver.hashCode() * ISBN.hashCode() * status.hashCode(); }
}