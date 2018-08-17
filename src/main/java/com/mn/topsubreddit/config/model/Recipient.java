package com.mn.topsubreddit.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *       name:
 *       number:
 *       email:
 */
public class Recipient {
    @JsonProperty
    private String name;
    @JsonProperty
    private String email;

    public Recipient() {
    }

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
