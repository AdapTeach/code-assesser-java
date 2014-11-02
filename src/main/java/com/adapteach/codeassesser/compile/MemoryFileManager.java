package com.adapteach.codeassesser.compile;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject.Kind;
import java.util.HashMap;
import java.util.Map;

class MemoryFileManager extends ForwardingJavaFileManager {

    final Map<String, Output> map = new HashMap<>();

    MemoryFileManager(JavaCompiler compiler) {
        super(compiler.getStandardFileManager(null, null, null));
    }

    @Override
    public Output getJavaFileForOutput(Location location, String name, Kind kind, FileObject source) {
        Output output = new Output(name, kind);
        this.map.put(name, output);
        return output;
    }
}