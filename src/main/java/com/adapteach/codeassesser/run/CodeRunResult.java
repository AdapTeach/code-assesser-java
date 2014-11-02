package com.adapteach.codeassesser.run;

import com.adapteach.codeassesser.model.SubmissionResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CodeRunResult {

    private boolean pass;

    private List<String> failedTestMessages = new ArrayList<>();

    public SubmissionResult asSubmissionResult() {
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setPass(pass);
        submissionResult.setFailedTestMessages(failedTestMessages);
        return submissionResult;
    }

}
