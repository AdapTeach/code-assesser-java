package com.adapteach.codegrader;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class CodeRunner {

    String compilationPath = "src/main/java/com/adapteach/codegrader/";

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public Result run(Submission submission) {
        JavaFileObject javaFileObject = asJavaFile(submission.getCode());
        compile(javaFileObject);
        Result result = new Result();
        result.setOut(runCompiled());
        return result;
    }

    private JavaFileObject asJavaFile(String code) {
        JavaFileObject javaFileObject = null;
        try {
            javaFileObject = new InMemoryJavaFileObject("Program.java", code);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return javaFileObject;
    }

    private void compile(JavaFileObject javaFileObject) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector diagnosticsCollector = new DiagnosticCollector();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        Iterable fileObjects = Arrays.asList(javaFileObject);
        CompilationTask task = compiler.getTask(null, fileManager, diagnosticsCollector, null, null, fileObjects);
        Boolean result = task.call();
        diagnosticsCollector.getDiagnostics().forEach((diagnostic) -> Console.log(diagnostic));
        if (result == true) {
            System.out.println("Compilation has succeeded");
        } else {
            System.out.println("Compilation fails.");
        }
    }

    private String runCompiled() {
        // Create a File object on the root of the directory containing the class file
        File file = new File("");

        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();          // file:/c:/myclasses/
            URL[] urls = {url};


            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);

            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany
            Class cls = cl.loadClass("Program");
            Object o = cls.getConstructor().newInstance();
            Method m = cls.getDeclaredMethod("execute");
            String r = (String) m.invoke(o);
            return r;

        } catch (Exception e) {
            return e.getMessage();
        }

//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }


}
