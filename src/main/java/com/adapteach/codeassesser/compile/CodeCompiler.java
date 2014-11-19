package com.adapteach.codeassesser.compile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeCompiler {

    public CompilationCheck checkCanCompile(List<CompilationUnit> compilationUnits) {
        MemoryClassLoader classLoader = new MemoryClassLoader(compilationUnits);
        return new CompilationCheck(classLoader);
    }

    public CompilationResult compile(List<CompilationUnit> compilationUnits) {
        MemoryClassLoader classLoader = new MemoryClassLoader(compilationUnits);
        CompilationResult compilation = new CompilationResult(classLoader);
        if (compilation.passed()) {
            compilation.setCompiledClasses(loadCompiledClasses(compilationUnits, classLoader));
        } else {
            return compilation;
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
                throw new RuntimeException(e); // Should have been checked earlier
            }
        });
        return compiledClasses;
    }
}
