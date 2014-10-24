package com.adapteach.codegrader.run;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CodeRunResult {

    private boolean pass;

    private List<String> failedTestMessages = new ArrayList<>();

}
