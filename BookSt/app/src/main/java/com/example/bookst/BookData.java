package com.example.bookst;

public class BookData {
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    private String bookName;

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    private String publisherName;

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    private String publishingYear;

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    private String Author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    public BookData(String bookName, String author, String publisherName, String publishingYear, String id, String location) {
        this.bookName = bookName;
        this.Author = author;
        this.publisherName = publisherName;
        this.publishingYear = publishingYear;
        this.id = id;
        this.location = location;
    }
    public BookData(){

    }
}
