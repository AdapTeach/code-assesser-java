package com.adapteach.codeassesser.run;

import com.adapteach.codeassesser.verify.TestClassCodeBuilder;

import java.lang.reflect.Method;
import java.util.List;

public class CodeRunner {

    public CodeRunResult run(CodeRunParams params) {
        CodeRunResult result;
        Class clazz = params.getCompilationResult().getCompiledClasses().get(TestClassCodeBuilder.TEST_CLASS);
        try {
            Object o = clazz.getConstructor().newInstance();
            Method m = clazz.getDeclaredMethod("execute");
            result = runExecuteMethod(o, m);
            if (result.getExceptionMessage() == null) {
                result.setPass(result.getFailedTestMessages().size() == 0);
            }
        } catch (Exception e) {
            result = new CodeRunResult();
            result.setExceptionMessage(e.toString());
        }
        return result;
    }

    private CodeRunResult runExecuteMethod(Object object, Method method) {
        CodeRunResult result = new CodeRunResult();

        Runnable runnable = () -> {
            try {
                result.setFailedTestMessages((List<String>) method.invoke(object));
                result.setCompleted(true);
            } catch (Exception e) {
                if (e.getCause() != null) {
                    result.setExceptionMessage(e.getCause().toString());
                } else {
                    result.setExceptionMessage(e.toString());
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join(2000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (!result.isCompleted() && result.getExceptionMessage() == null) {
            result.setExceptionMessage("Submission took too long to run");
        }

        return result;
    }


}
