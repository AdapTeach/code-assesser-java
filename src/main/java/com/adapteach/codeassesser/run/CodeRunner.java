package com.adapteach.codeassesser.run;

import com.adapteach.codeassesser.verify.TestClassCodeBuilder;

import java.lang.reflect.Method;
import java.util.List;

public class CodeRunner {

    public CodeRunResult run(CodeRunParams params) {
        CodeRunResult result = new CodeRunResult();
        Class clazz = params.getCompilationResult().getCompiledClasses().get(TestClassCodeBuilder.TEST_CLASS);
        try {
            Object o = clazz.getConstructor().newInstance();
            Method m = clazz.getDeclaredMethod("execute");
            result.setFailedTestMessages((List<String>) m.invoke(o));
            result.setPass(result.getFailedTestMessages().size() == 0);
        } catch (Exception e) {
            result.setPass(false);
            if (e.getCause() != null) {
                result.setExceptionMessage(e.getCause().toString());
            } else {
                result.setExceptionMessage(e.toString());
            }
        }
        return result;
    }


}
