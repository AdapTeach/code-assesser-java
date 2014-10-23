package com.adapteach.codegrader.model;

import com.google.api.client.util.Key;

public class SubmissionJson {

    @Key
    public AssessmentJson assessment;

    @Key
    public String code;

}
