package com.example.adam.lab7.model.json;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private int id;
    private transient Date dateDate;
    private String date;
    private String user;
    private String subject;
    private String content;

    public Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDate() {
        return dateDate;
    }

    public void setDateDate(Date dateDate) {
        this.dateDate = dateDate;
        date = new SimpleDateFormat("yyyy-MM-dd").format(dateDate);
    }

    public String getDate() {
        return date;

    }

    public void setDate(String date) {
        try {
            this.date = date;
            this.dateDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
