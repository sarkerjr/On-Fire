package com.sarkerjr.onfire;

public class Note {
    private String text;
    private String title;
    private String key;

    public Note() {
    }

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Note(String title, String text, String key) {
        this.title = title;
        this.text = text;
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
