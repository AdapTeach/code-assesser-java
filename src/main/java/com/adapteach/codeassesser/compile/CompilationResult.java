package com.adapteach.codeassesser.compile;

import lombok.Data;

import java.util.Map;

@Data
public class CompilationResult extends CompilationCheck {

    private Map<String, Class> compiledClasses;

    public CompilationResult(MemoryClassLoader classLoader) {
        super(classLoader);
    }

}
