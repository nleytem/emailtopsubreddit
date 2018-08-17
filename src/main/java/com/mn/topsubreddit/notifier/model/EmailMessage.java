package com.mn.topsubreddit.notifier.model;

import com.mn.topsubreddit.config.model.Recipient;
import com.mn.topsubreddit.retriever.model.Result;
import com.sun.javafx.binding.StringFormatter;

import java.util.List;

public class EmailMessage {
    private List<Result> results;
    private Recipient recipient;

    public EmailMessage(List<Result> results, Recipient recipient) {
        this.results = results;
        this.recipient = recipient;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
