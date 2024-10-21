package com.example.request;


public class SendMailRequest {
    private Person from;
    private Person to;
    private String subject;
    private String html;

    public Person getFrom() {
        return from;
    }

    public void setFrom(Person from) {
        this.from = from;
    }

    private String text;
}


class Person{
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}