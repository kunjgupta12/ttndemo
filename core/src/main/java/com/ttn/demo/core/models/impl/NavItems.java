package com.ttn.demo.core.models.impl;

public class NavItems {
    private String title;
    private String link;
    private String isOpen;
    public NavItems(String title, String link,String isOpen) {
        this.title = title;
        this.link = link;
        this.isOpen=isOpen;
    }
    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
    public String getIsOpen(){return isOpen;}
}

