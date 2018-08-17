package com.mn.topsubreddit.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth {
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private String app_id;
    @JsonProperty
    private String app_secret;
    @JsonProperty
    private String email_username;
    @JsonProperty
    private String email_password;

    public Auth() {
    }

    public Auth(String username, String password, String app_id, String app_secret,
                String email_username, String email_password) {
        this.username = username;
        this.password = password;
        this.app_id = app_id;
        this.app_secret = app_secret;
        this.email_username = email_username;
        this.email_password = email_password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public String getEmail_username() {
        return email_username;
    }

    public void setEmail_username(String email_username) {
        this.email_username = email_username;
    }

    public String getEmail_password() {
        return email_password;
    }

    public void setEmail_password(String email_password) {
        this.email_password = email_password;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", app_id='" + app_id + '\'' +
                ", app_secret='" + app_secret + '\'' +
                '}';
    }
}
