package com.adapteach.codegrader.run;

import com.adapteach.codegrader.compile.MemoryClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;

public class CodeRunner {

    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public CodeRunResult run(CodeRunParams params) {
        CodeRunResult result = new CodeRunResult();
        result.setPass(execute(params, result));
        return result;
    }

    private boolean execute(CodeRunParams params, CodeRunResult result) {
        Class clazz = loadClass(params, result);
        try {
            Object o = clazz.getConstructor().newInstance();
            Method m = clazz.getDeclaredMethod("execute");
            boolean pass = (boolean) m.invoke(o);
            return pass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class loadClass(CodeRunParams params, CodeRunResult result) {
        MemoryClassLoader classLoader = new MemoryClassLoader(params.getClassName(), params.getCode(), result.getOut());
        try {
            return classLoader.loadClass(params.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
