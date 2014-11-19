package com.adapteach.codeassesser.compile;

import lombok.Getter;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject.Kind;
import javax.tools.ToolProvider;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

class MemoryClassLoader extends ClassLoader {

    private final List<CompilationUnit> compilationUnits;
    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final MemoryFileManager fileManager = new MemoryFileManager(this.compiler);

    @Getter
    private final Writer out = new StringWriter();
    @Getter
    private final DiagnosticCollector diagnosticListener = new DiagnosticCollector<>();

    MemoryClassLoader(List<CompilationUnit> compilationUnits) {
        this.compilationUnits = compilationUnits;
        List<Source> sources = new ArrayList<>();
        compilationUnits.forEach((compilationUnit) -> {
            String className = compilationUnit.getName();
            String code = compilationUnit.getCode();
            sources.add(new Source(className, Kind.SOURCE, code));
        });
        this.compiler.getTask(out, fileManager, diagnosticListener, null, null, sources).call();
    }

    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        synchronized (fileManager) {
            Output output = fileManager.map.remove(name);
            if (output != null) {
                byte[] array = output.toByteArray();
                return defineClass(name, array, 0, array.length);
            }
        }
        return super.findClass(name);
    }

    boolean hasCompilationErrors() {
        return diagnosticListener.getDiagnostics().size() > 0;
    }

    List<String> getCompilationErrors() {
        List<String> compilationErrors = new ArrayList<>();
        getDiagnosticListener().getDiagnostics().forEach((diagnostic) -> compilationErrors.add(diagnostic.toString()));
        return compilationErrors;
    }

    List<CompilationUnit> getMissingCompilationUnits() {
        List<CompilationUnit> missingCompilationUnits = new ArrayList<>();
        compilationUnits.forEach((compilationUnit) -> {
            try {
                loadClass(compilationUnit.getName());
            } catch (ClassNotFoundException e) {
                missingCompilationUnits.add(compilationUnit);
            }
        });
        return missingCompilationUnits;
    }

}