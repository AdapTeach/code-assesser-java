package com.adapteach.codegrader.model;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionResult {

    private boolean pass;

    private List<String> compilationErrors;

}
