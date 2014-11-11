package com.adapteach.codeassesser.model;

import com.google.api.client.util.Key;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class AssessmentJson {

    @Key
    public List<CompilationUnit> providedCompilationUnits;

    @Key
    public List<TestJson> tests = new ArrayList<>();

}
