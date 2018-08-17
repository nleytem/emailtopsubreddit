package com.mn.topsubreddit.retriever;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mn.topsubreddit.app.Application;
import com.mn.topsubreddit.config.model.Subreddit;
import com.mn.topsubreddit.retriever.model.Result;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Retriever implements AutoCloseable {
    private List<Subreddit> subreddits;
    private String authToken;
    private JerseyClient client;

    public Retriever(List<Subreddit> subreddits, String authToken) {
        this.subreddits = subreddits;
        this.authToken = authToken;
        this.client = JerseyClientBuilder.createClient();
    }

    public List<Result> getResults() throws IOException {
        List<Result> results = new ArrayList<>();
        for(Subreddit subreddit : this.subreddits) {
            results.addAll(getResultsForSubreddit(subreddit));
        }
        return results;
    }
    private List<Result> getResultsForSubreddit(Subreddit reddit) throws IOException {
        List<Result> results = new ArrayList<>();
//        URI uri = new JerseyUriBuilder()
//                .path("https://www.reddit.com/r/" + reddit.getName() + "/.json")
//                .queryParam("access_token", this.authToken).build();
//        System.out.println(uri.toURL());
        WebTarget target = this.client.target("https://www.reddit.com").path("r/" + reddit.getName() + "/.json")
                .queryParam("t", "day")
                .queryParam("access_token", this.authToken);
        Response response = target.request().header(HttpHeaders.USER_AGENT, Application.USER_AGENT).get();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode object = mapper.readTree(response.readEntity(String.class));
        JsonNode children = object.get("data").get("children");
        int i = 0;
        for(int j = 0; j < children.size() && i < reddit.getCount(); j++) {
            JsonNode node = children.get(j).get("data");
            if (node.get("stickied").asBoolean() || !node.get("selftext").asText().equals("")) {
                continue;
            }
            String url;
            if (node.get("is_video").asBoolean()) {
                url = node.get("media").get("reddit_video").get("fallback_url").asText();
            } else {
                url = node.get("url").asText();
            }
            results.add(new Result(url, node.get("title").asText()));
            ++i;
        }
        return results;
    }

    public void close() {
        this.client.close();
    }
    public List<Subreddit> getSubreddits() {
        return subreddits;
    }

    public void setSubreddits(List<Subreddit> subreddits) {
        this.subreddits = subreddits;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Retriever{" +
                "subreddits=" + subreddits +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
