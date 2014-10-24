package com.adapteach.codegrader.compile;

import java.util.ArrayList;
import java.util.List;

public class CodeCompiler {

    public CompilationResult compile(String className, String code) {
        MemoryClassLoader classLoader = new MemoryClassLoader(className, code);
        CompilationResult compilation = new CompilationResult();
        if (classLoader.hasCompilationErrors()) {
            compilation.setSuccess(false);
            List<String> compilationErrors = new ArrayList<>();
            classLoader.getDiagnosticListener().getDiagnostics().forEach((diagnostic) -> compilationErrors.add(diagnostic.toString()));
            compilation.setCompilationErrors(compilationErrors);
        } else {
            compilation.setSuccess(true);
            try {
                Class clazz = classLoader.loadClass(className);
                compilation.setCompiledClass(clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return compilation;
    }

}
