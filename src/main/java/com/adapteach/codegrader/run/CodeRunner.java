package com.adapteach.codegrader.run;

import java.lang.reflect.Method;

public class CodeRunner {

    public CodeRunResult run(CodeRunParams params) {
        CodeRunResult result = new CodeRunResult();
        result.setPass(execute(params, result));
        return result;
    }

    private boolean execute(CodeRunParams params, CodeRunResult result) {
        Class clazz = params.getCompilationResult().getCompiledClass();
        try {
            Object o = clazz.getConstructor().newInstance();
            Method m = clazz.getDeclaredMethod("execute");
            return (boolean) m.invoke(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
