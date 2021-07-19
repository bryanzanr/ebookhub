package com.herokuapp.ebookhub.book.dto.request;

public class CreateRequest {

    private String imgPath;

    private String title;

    private String author;

    private String publisher;

    private String description;

    private Integer stock;

    private String category;

    public String getImgPath() {
        return this.imgPath;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getStock() {
        return this.stock;
    }

    public String getCategory() {
        return this.category;
    }

}