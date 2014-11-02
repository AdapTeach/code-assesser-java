package com.adapteach.codeassesser.model;

import com.google.api.client.util.Key;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
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
