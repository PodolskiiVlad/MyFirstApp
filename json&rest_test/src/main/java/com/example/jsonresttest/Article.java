package com.example.jsonresttest;

import java.util.Date;

class Article {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;
    private long id;

    Article(String author,
            String title,
            String description,
            String url,
            String urlToImage,
            Date publishedAt,
            long id) {

        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.id = id;
    }

    String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    String getUrl() {
        return url;
    }

    String getUrlToImage() {
        return urlToImage;
    }

    Date getPublishedAt() {
        return publishedAt;
    }

    public long getId() {
        if (id == 0){
            id = initId();
        }
        return id;
    }

    private long initId(){
        int firstArg = 0;
        long secondArg = 0;
        int thirdArg = 0;
        int forthArg = 0;

        if (description != null){
            firstArg = description.length();
        }
        if (publishedAt != null){
            secondArg = publishedAt.getTime();
        }
        if (url != null){
            firstArg = url.length();
        }
        if (title != null){
            firstArg = title.length();
        }
        return firstArg + secondArg + thirdArg + forthArg;
    }
}
