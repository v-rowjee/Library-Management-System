package com.example.librarymanagementsystem;

public class BookDB {
    private String title;
    private Integer book_id;
    private String author;

    public BookDB(Integer book_id, String title, String author) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
