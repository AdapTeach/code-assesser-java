package com.adapteach.codeassesser.web;

import com.adapteach.codeassesser.verify.Submission;
import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    public Submission parse(String json) {
        return gson.fromJson(json, Submission.class);
    }

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
