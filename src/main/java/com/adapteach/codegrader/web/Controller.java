package com.adapteach.codegrader.web;

import com.adapteach.codegrader.Console;
import com.adapteach.codegrader.model.Submission;
import com.adapteach.codegrader.verify.SubmissionVerifier;

import static spark.Spark.post;

public class Controller {

    public static final String basePath = "/v1/";

    private final JsonTransformer jsonTransformer = new JsonTransformer();
    private final SubmissionVerifier verifier = new SubmissionVerifier();

    public void publish() {
        post(basePath, (request, response) -> {
            Submission submission = jsonTransformer.parse(request.body());
            return verifier.verify(submission);
        }, jsonTransformer);
    }

}
