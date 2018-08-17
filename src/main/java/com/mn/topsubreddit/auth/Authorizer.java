package com.mn.topsubreddit.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mn.topsubreddit.app.Application;
import com.mn.topsubreddit.config.model.Auth;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.IOException;


public class Authorizer {

    private final String API_URL = "https://www.reddit.com/api/v1";
    private String username;
    private String password;
    private String appId;
    private String appSecret;

    public Authorizer(Auth auth) {
        this(auth.getUsername(), auth.getPassword(), auth.getApp_id(), auth.getApp_secret());
    }

    public Authorizer(String username, String password, String appId, String appSecret) {
        this.username = username;
        this.password = password;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getOauthToken() throws IOException {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(this.appId, this.appSecret);
        JerseyClient client = JerseyClientBuilder.createClient();
        client.register(feature);
        JerseyWebTarget webTarget = client.target(API_URL).path("access_token");
        MultivaluedHashMap<String,String> formData = new MultivaluedHashMap<>();
        formData.add("grant_type", "password");
        formData.add("username", this.username);
        formData.add("password", this.password);
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.USER_AGENT, Application.USER_AGENT).post(Entity.form(formData));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode object = mapper.readTree(response.readEntity(String.class));
        client.close();
        return object.get("access_token").asText();
    }
}
