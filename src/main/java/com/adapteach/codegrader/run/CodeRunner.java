package com.adapteach.codegrader.run;

import com.adapteach.codegrader.model.Result;
import com.adapteach.codegrader.model.Submission;
import com.adapteach.codegrader.compile.MemoryClassLoader;

import javax.tools.*;
import java.lang.reflect.Method;

public class CodeRunner {

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public Result run(Submission submission) {
        Result result = new Result();

        Class program = loadClass(submission);
        String output = execute(program);
        result.setOut(output);
        return result;
    }

    private Class loadClass(Submission submission) {
        MemoryClassLoader classLoader = new MemoryClassLoader("Program", submission.getCode());
        try {
            return classLoader.loadClass("Program");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String execute(Class program) {
        try {
            Object o = program.getConstructor().newInstance();
            Method m = program.getDeclaredMethod("execute");
            String r = (String) m.invoke(o);
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
