package com.adapteach.codegrader.compile;

import lombok.Data;

import java.util.List;

@Data
public class CompilationResult {

    private boolean success;

    private Class compiledClass;

    private List<String> compilationErrors;

}
