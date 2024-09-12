package com.example.response;


public class Message{
    String id;
    String subject;
    String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    From from;
}

class From{
    String name;
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}