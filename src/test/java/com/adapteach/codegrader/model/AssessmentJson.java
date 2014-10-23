package com.adapteach.codegrader.model;

import com.google.api.client.util.Key;

import java.util.ArrayList;
import java.util.List;

public class AssessmentJson {

    @Key
    public String title;

    @Key
    public String instructions;

    @Key
    public String className;

    @Key
    public String startCode;

    @Key
    public List<TestJson> tests = new ArrayList<>();

}
