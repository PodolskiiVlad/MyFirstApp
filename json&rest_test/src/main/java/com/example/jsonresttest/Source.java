package com.example.jsonresttest;

class Source {

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

    Source(String id, String name, String description, String url, String category, String language, String country) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    String getUrl() {
        return url;
    }

    String getCategory() {
        return category;
    }

    String getLanguage() {
        return language;
    }

    String getCountry() {
        return country;
    }
}
