package com.adapteach.codeassesser.run;

import com.adapteach.codeassesser.compile.CompilationResult;
import lombok.Data;

@Data
public class CodeRunParams {

    private CompilationResult compilationResult;

    private String methodName;

}
