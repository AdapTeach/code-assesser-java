package com.adapteach.codeassesser.run;

import java.lang.reflect.Method;
import java.util.List;

public class CodeRunner {

    public CodeRunResult run(CodeRunParams params) {
        CodeRunResult result = new CodeRunResult();
        Class clazz = params.getCompilationResult().getCompiledClass();
        try {
            Object o = clazz.getConstructor().newInstance();
            Method m = clazz.getDeclaredMethod("execute");
            result.setFailedTestMessages((List<String>) m.invoke(o));
            result.setPass(result.getFailedTestMessages().size() == 0);
        } catch (Exception e) {
            result.setPass(false);
            result.setExceptionMessage(e.getCause().toString());
        }
        return result;
    }


}
