package com.example.jsonresttest;

import java.util.List;

public class ArticleList {

    private String status;
    private String sortBy;
    private List<Article> articles;

    public ArticleList(String status, String sortBy, List<Article> articleList) {
        this.status = status;
        this.sortBy = sortBy;
        this.articles = articleList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticlesList() {
        return articles;
    }

    public void setArticlesList(List<Article> articleList) {
        this.articles = articleList;
    }
}
