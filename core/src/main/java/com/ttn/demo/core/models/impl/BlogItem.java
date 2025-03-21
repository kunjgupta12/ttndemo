package com.ttn.demo.core.models.impl;

public   class BlogItem {
    private final String title;
    private final String date;
    private final String description;
    private final String link;
    private final String image;

    public BlogItem(String title, String date, String description, String link, String image) {
        this.title = title;
        this.date = date;
        this.description = truncate(description);
        this.link = link;
        this.image = image;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getLink() { return link; }
    public String getImage() { return image; }
    private String truncate(String text) {
        if (text == null || text.length() <= 150) {
            return text;
        }
        return text.substring(0, 150) + "...";
    }
}