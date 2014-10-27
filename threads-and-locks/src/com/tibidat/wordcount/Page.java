package com.tibidat.wordcount;

// Represents a single page
public class Page {
    private String title;
    private String text;

    public Page() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
