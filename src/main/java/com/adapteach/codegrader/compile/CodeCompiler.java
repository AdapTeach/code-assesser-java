package com.adapteach.codegrader.compile;

public class CodeCompiler {

    public CompilationResult compile(String className, String code) {
        MemoryClassLoader classLoader = new MemoryClassLoader(className, code);
        CompilationResult compilation = new CompilationResult();
        if (classLoader.hasCompilationErrors()) {
            compilation.setSuccess(false);
            StringBuilder sb = new StringBuilder();
            classLoader.getDiagnosticListener().getDiagnostics().forEach(sb::append);
            compilation.setCompilationErrors(sb.toString());
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
