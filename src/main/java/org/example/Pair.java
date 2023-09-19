package org.example;

public class Pair {
    public Pair(String url, String title) {
        this.url = url;
        this.title = title;
    }

    private String url;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url + " " + title;
    }

    public boolean isIndex() {
        return url.contains("index");
    }
}
