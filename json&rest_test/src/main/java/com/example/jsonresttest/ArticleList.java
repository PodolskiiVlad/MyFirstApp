package com.example.jsonresttest;

import java.util.List;

public class ArticleList {

    private List<Article> articles;

    public ArticleList(List<Article> articleList) {
        this.articles = articleList;
    }

    public List<Article> getArticlesList() {
        return articles;
    }
}
