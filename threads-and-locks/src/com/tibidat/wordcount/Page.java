package com.tibidat.wordcount;

// Represents a single page
public class Page {
    private StringBuilder sb = new StringBuilder();
    private String title;

    public Page() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return sb.toString();
    }

    public void setText(String text) {
        sb.append(text);
    }
}
