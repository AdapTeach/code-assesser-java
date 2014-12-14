package com.adapteach.codeassesser.web;

import com.adapteach.codeassesser.verify.Submission;
import com.adapteach.codeassesser.verify.SubmissionVerifier;
import com.google.inject.Inject;

import static spark.Spark.post;

public class Controller {

    public static final String basePath = "/v1/";

    private final JsonTransformer jsonTransformer;
    private final SubmissionVerifier verifier;

    @Inject
    public Controller(JsonTransformer jsonTransformer, SubmissionVerifier submissionVerifier) {
        this.jsonTransformer = jsonTransformer;
        this.verifier = submissionVerifier;
    }

    public void publish() {
        post(basePath, (request, response) -> {
            Submission submission = jsonTransformer.parse(request.body());
            return verifier.verify(submission);
        }, jsonTransformer);
    }

}
