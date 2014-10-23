package com.adapteach.codegrader.compile;

import lombok.Data;

@Data
public class CompilationResult {

    private boolean success;

    private Class compiledClass;

    private String compilationErrors;

}
