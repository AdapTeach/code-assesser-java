package com.adapteach.codegrader.model;

import com.google.api.client.util.Key;

public class SubmissionResultJson {

    @Key
    public boolean pass;

    @Key
    public String out;

    @Key
    public String err;

}
