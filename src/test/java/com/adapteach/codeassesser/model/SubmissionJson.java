package com.adapteach.codeassesser.model;

import com.google.api.client.util.Key;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class SubmissionJson {

    @Key
    public AssessmentJson assessment;

    @Key
    public List<CompilationUnitJson> submittedCompilationUnits = new ArrayList<>();

}
