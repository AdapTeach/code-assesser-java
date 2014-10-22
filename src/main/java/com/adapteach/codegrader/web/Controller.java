package com.adapteach.codegrader.web;

import com.adapteach.codegrader.model.Submission;
import com.adapteach.codegrader.run.CodeRunner;

import static spark.Spark.post;

public class Controller {

    public static final String basePath = "/v1/";

    private final JsonTransformer jsonTransformer = new JsonTransformer();
    private final CodeRunner runner = new CodeRunner();

    public void publish() {
        post(basePath, (request, response) -> {
            Submission submission = jsonTransformer.parse(request.body());
            return runner.run(submission);
        }, jsonTransformer);
    }

}
