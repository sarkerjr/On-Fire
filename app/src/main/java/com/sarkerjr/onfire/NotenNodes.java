package com.sarkerjr.onfire;

public class NotenNodes {
    private String key;
    private String title;
    private String text;

    public NotenNodes() {
    }

    public NotenNodes(String title, String text, String key) {
        this.title = title;
        this.text = text;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
