package com.mn.topsubreddit.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Subreddit {
    @JsonProperty
    private String name;
    @JsonProperty
    private int count;

    public Subreddit() {
    }

    public Subreddit(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
