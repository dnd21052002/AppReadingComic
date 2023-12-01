package com.example.appreadingcomic.object;

public class Account {
    private int Id;
    private String userName;
    private String passWord;
    private String Email;
    private int decentralization;

    public Account(String userName, String email, int decentralization) {
        this.userName = userName;
        Email = email;
        this.decentralization = decentralization;
    }

    public Account(String userName, String passWord, String email, int decentralization) {
        this.userName = userName;
        this.passWord = passWord;
        Email = email;
        this.decentralization = decentralization;
    }

    public Account(String userName, String Email) {
        this.userName = userName;
        this.Email = Email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getDecentralization() {
        return decentralization;
    }

    public void setDecentralization(int decentralization) {
        this.decentralization = decentralization;
    }
}