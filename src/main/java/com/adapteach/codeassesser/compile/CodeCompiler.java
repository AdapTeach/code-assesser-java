package com.adapteach.codeassesser.compile;

import com.adapteach.codeassesser.model.CompilationUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeCompiler {

    public CompilationResult compile(List<CompilationUnit> compilationUnits) {
        MemoryClassLoader classLoader = new MemoryClassLoader(compilationUnits);
        CompilationResult compilation = new CompilationResult();
        if (classLoader.hasCompilationErrors()) {
            compilation.setSuccess(false);
            List<String> compilationErrors = new ArrayList<>();
            classLoader.getDiagnosticListener().getDiagnostics().forEach((diagnostic) -> compilationErrors.add(diagnostic.toString()));
            compilation.setCompilationErrors(compilationErrors);
        } else {
            compilation.setSuccess(true);
            compilation.setCompiledClasses(loadCompiledClasses(compilationUnits, classLoader));
        }
        return compilation;
    }

    private Map<String, Class> loadCompiledClasses(List<CompilationUnit> compilationUnits, ClassLoader classLoader) {
        Map<String, Class> compiledClasses = new HashMap<>();
        compilationUnits.forEach((compilationUnit) -> {
            try {
                Class clazz = classLoader.loadClass(compilationUnit.getName());
                compiledClasses.put(compilationUnit.getName(), clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return compiledClasses;
    }
}
