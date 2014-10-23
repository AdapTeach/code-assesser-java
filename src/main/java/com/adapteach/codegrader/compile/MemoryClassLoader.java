package com.adapteach.codegrader.compile;

import lombok.Getter;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject.Kind;
import javax.tools.ToolProvider;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class MemoryClassLoader extends ClassLoader {

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final MemoryFileManager fileManager = new MemoryFileManager(this.compiler);

    @Getter
    private final Writer out = new StringWriter();
    @Getter
    private final DiagnosticCollector diagnosticListener = new DiagnosticCollector<>();

    public MemoryClassLoader(String className, String code) {
        this(Collections.singletonMap(className, code));
    }

    /**
     * @param map The list of java codes mapped by class names
     */
    public MemoryClassLoader(Map<String, String> map) {
        List<Source> sources = new ArrayList<>();
        map.entrySet().forEach((Map.Entry<String, String> fileContent) -> {
            String className = fileContent.getKey();
            String code = fileContent.getValue();
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

}