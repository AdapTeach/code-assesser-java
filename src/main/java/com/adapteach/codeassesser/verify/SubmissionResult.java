package com.adapteach.codeassesser.verify;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubmissionResult {

    private boolean pass;

    private List<String> compilationErrors = new ArrayList<>();

    private String exceptionMessage;

    private List<String> failedTestMessages = new ArrayList<>();

}
