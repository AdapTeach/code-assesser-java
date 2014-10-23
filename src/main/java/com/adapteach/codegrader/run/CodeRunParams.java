package com.adapteach.codegrader.run;

import com.adapteach.codegrader.compile.CompilationResult;
import lombok.Data;

@Data
public class CodeRunParams {

    private CompilationResult compilationResult;

    private String methodName;

}
