package com.adapteach.codegrader.run;

import com.adapteach.codegrader.model.SubmissionResult;
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
