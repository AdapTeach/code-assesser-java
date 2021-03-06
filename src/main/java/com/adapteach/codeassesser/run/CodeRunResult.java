package com.adapteach.codeassesser.run;

import com.adapteach.codeassesser.verify.SubmissionResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CodeRunResult {

    private boolean pass = false;

    private String exceptionMessage;

    private List<String> failedTestMessages = new ArrayList<>();

    private boolean completed = false;

    public SubmissionResult asSubmissionResult() {
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setPass(pass);
        submissionResult.setExceptionMessage(exceptionMessage);
        submissionResult.setFailedTestMessages(failedTestMessages);
        return submissionResult;
    }

}
