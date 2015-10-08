package com.aurovo.tack.entities;

/**
 * Basic message collection 
 */
public class Message extends BaseCollection {
    
    //Declares the state
    public enum STATE {
        CREATED,
        PENDING,
        PROCESSING,
        COMPLETED
    }
    
    //State of the message
    private STATE state;
    
    //0 to 100
    private int percentage;
    
    //Message
    private String message;

    //Default constructor
    public Message() {
        this.state = STATE.CREATED;
        this.percentage = 0;
        this.message = "";        
    }
    
    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
