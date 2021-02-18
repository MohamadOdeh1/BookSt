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

    public BookData(String bookName, String author, String publisherName, String publishingYear) {
        this.bookName = bookName;
        this.Author = author;
        this.publisherName = publisherName;
        this.publishingYear = publishingYear;
    }
}
