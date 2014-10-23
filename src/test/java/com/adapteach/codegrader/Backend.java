package com.adapteach.codegrader;

import com.adapteach.codegrader.model.SubmissionJson;
import com.adapteach.codegrader.model.SubmissionResultJson;
import com.adapteach.codegrader.web.Controller;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

public class Backend {

    private static final int PORT_NUMBER = 5021;
    protected static final String BASE_PATH = "http://localhost:" + PORT_NUMBER + Controller.basePath;
    private static final Server server = new Server(PORT_NUMBER);

    protected static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory((request) -> request.setParser(new JsonObjectParser(new GsonFactory())));

    public Backend() {
        server.start();
    }

    public SubmissionResultJson submit(SubmissionJson submission) throws IOException {
        return requestFactory
                .buildPostRequest(url(), asJson(submission))
                .execute()
                .parseAs(SubmissionResultJson.class);
    }

    private GenericUrl url() {
        return new GenericUrl(BASE_PATH);
    }

    private HttpContent asJson(SubmissionJson submission) {
        return new JsonHttpContent(new GsonFactory(), submission);
    }

}
