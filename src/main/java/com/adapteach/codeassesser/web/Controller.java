package com.adapteach.codeassesser.web;

import com.adapteach.codeassesser.verify.Submission;
import com.adapteach.codeassesser.verify.SubmissionVerifier;

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
