package com.example.librarymanagementsystem;

public class AccountDB {
    private Integer acc_id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Integer current_book;

    //default constructor
    public AccountDB() {}

    public AccountDB(Integer acc_id, String username, String password, String firstname, String lastname, Integer current_book) {
        this.acc_id = acc_id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.current_book = current_book;
    }

    public void setAccount(Integer acc_id, String username, String password, String firstname, String lastname, Integer current_book) {
        this.acc_id = acc_id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.current_book = current_book;
    }



    public Integer getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(Integer acc_id) {
        this.acc_id = acc_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getCurrent_book() {
        return current_book;
    }

    public void setCurrent_book(Integer current_book) {
        this.current_book = current_book;
    }
}
