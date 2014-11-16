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
            List<CompilationUnit> missingCompilationUnits = checkCompiled(compilationUnits, classLoader);
            if (missingCompilationUnits.size() == 0) {
                compilation.setSuccess(true);
                compilation.setCompiledClasses(loadCompiledClasses(compilationUnits, classLoader));
            } else {
                compilation.setSuccess(false);
                List<String> messages = new ArrayList<>();
                missingCompilationUnits.forEach((compilationUnit) -> messages.add("Missing " + compilationUnit.getName() + " " + compilationUnit.getKind()));
                compilation.setCompilationErrors(messages);
            }
        }
        return compilation;
    }

    private List<CompilationUnit> checkCompiled(List<CompilationUnit> compilationUnits, ClassLoader classLoader) {
        List<CompilationUnit> missingCompilationUnits = new ArrayList<>();
        compilationUnits.forEach((compilationUnit) -> {
            try {
                classLoader.loadClass(compilationUnit.getName());
            } catch (ClassNotFoundException e) {
                missingCompilationUnits.add(compilationUnit);
            }
        });
        return missingCompilationUnits;
    }

    private Map<String, Class> loadCompiledClasses(List<CompilationUnit> compilationUnits, ClassLoader classLoader) {
        Map<String, Class> compiledClasses = new HashMap<>();
        compilationUnits.forEach((compilationUnit) -> {
            try {
                Class clazz = classLoader.loadClass(compilationUnit.getName());
                compiledClasses.put(compilationUnit.getName(), clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e); // Should have been checked earlier
            }
        });
        return compiledClasses;
    }
}
