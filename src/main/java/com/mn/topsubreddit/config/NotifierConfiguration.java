package com.mn.topsubreddit.config;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mn.topsubreddit.config.model.Auth;
import com.mn.topsubreddit.config.model.Recipient;
import com.mn.topsubreddit.config.model.Subreddit;

import java.util.List;

public class NotifierConfiguration {
    @JsonProperty
    private Auth auth;
    @JsonProperty
    private List<Subreddit> subreddits;
    @JsonProperty
    private List<Recipient> recipients;

    public NotifierConfiguration() {
    }

    public NotifierConfiguration(Auth auth, List<Subreddit> subreddits, List<Recipient> recipients) {
        this.auth = auth;
        this.subreddits = subreddits;
        this.recipients = recipients;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public List<Subreddit> getSubreddits() {
        return subreddits;
    }

    public void setSubreddits(List<Subreddit> subreddits) {
        this.subreddits = subreddits;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "NotifierConfiguration{" +
                "auth=" + auth +
                ", subreddits=" + subreddits +
                ", recipients=" + recipients +
                '}';
    }
}
