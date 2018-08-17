package com.mn.topsubreddit.retriever.model;

public class Result {
    private String link;
    private String title;

    public Result(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Result{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
