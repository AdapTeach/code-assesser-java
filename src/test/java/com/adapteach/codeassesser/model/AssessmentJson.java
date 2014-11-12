package com.adapteach.codeassesser.model;

import com.google.api.client.util.Key;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class AssessmentJson {

    @Key
    public List<CompilationUnitJson> providedCompilationUnits = new ArrayList<>();

    @Key
    public List<TestJson> tests = new ArrayList<>();

}
