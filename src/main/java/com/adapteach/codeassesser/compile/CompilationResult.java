package com.adapteach.codeassesser.compile;

import com.adapteach.codeassesser.model.SubmissionResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

@Data
public class CompilationResult {

    private boolean success;

    private List<String> compilationErrors = new ArrayList<>();

    public Map<String, Class> compiledClasses;

    public SubmissionResult asSubmissionResult() {
        checkState(!success, "Should be called only for failed compilations");
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setPass(success);
        submissionResult.setCompilationErrors(compilationErrors);
        return submissionResult;
    }

}
