package com.mn.topsubreddit.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mn.topsubreddit.auth.Authorizer;
import com.mn.topsubreddit.config.NotifierConfiguration;
import com.mn.topsubreddit.config.model.Auth;
import com.mn.topsubreddit.notifier.Notifier;
import com.mn.topsubreddit.retriever.Retriever;
import com.mn.topsubreddit.retriever.model.Result;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application implements AutoCloseable {
    private NotifierConfiguration configuration;
    private Notifier notifier;
    private Retriever retriever;
    private Auth auth;
    public static final String USER_AGENT = System.getProperty("os.name") + ":com.mn.topsubreddit:v1"
            + " (by /u/tedsemporiumemporium";

    public Application(String configPath) throws IOException {
        NotifierConfiguration config = this.readConfiguration(configPath);
        this.auth = config.getAuth();
        Authorizer authorizer = new Authorizer(auth);

        this.retriever = new Retriever(config.getSubreddits(), authorizer.getOauthToken());
        this.notifier = new Notifier(config.getRecipients(), auth);
    }

    public Application(Notifier notifier, Retriever retriever, Auth auth) {
        this.notifier = notifier;
        this.retriever = retriever;
        this.auth = auth;
    }

    public void run() throws IOException {
        List<Result> results = this.retriever.getResults();
        this.notifier.setResults(results);
        this.notifier.sendNotifications();
        this.retriever.close();
    }

    public void sendNotifications() {
        this.notifier.sendNotifications();
    }
    public NotifierConfiguration readConfiguration(String path) throws IOException {
        File file = new File(path);
        return this.readConfiguration(file);
    }

    public NotifierConfiguration readConfiguration(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper.readValue(file, NotifierConfiguration.class);
    }

    public void close() {
        if (this.retriever != null) {
            this.retriever.close();
        }
    }

    public NotifierConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(NotifierConfiguration configuration) {
        this.configuration = configuration;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public Retriever getRetriever() {
        return retriever;
    }

    public void setRetriever(Retriever retriever) {
        this.retriever = retriever;
    }

    public static void main(String[] args) throws IOException {
        String path = args[0];
        try(Application application = new Application(path)) {
            application.run();
        }
    }
}
