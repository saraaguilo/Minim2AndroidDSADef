package edu.upc.dsa.restproject.models;

public class Abuse{
    String date;
    String informer;
    String message;

    public Abuse(){}

    public Abuse(String date, String informer, String message) {
        this.date = date;
        this.informer = informer;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInformer() {
        return informer;
    }

    public void setInformer(String informer) {
        this.informer = informer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
