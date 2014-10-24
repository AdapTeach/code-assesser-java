package com.adapteach.codegrader.compile;

import com.adapteach.codegrader.model.SubmissionResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Data
public class CompilationResult {

    private boolean success;

    private Class compiledClass;

    private List<String> compilationErrors = new ArrayList<>();

    public SubmissionResult asSubmissionResult() {
        checkState(!success, "Should be called only for failed compilations");
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setPass(success);
        submissionResult.setCompilationErrors(compilationErrors);
        return submissionResult;
    }

}
