package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.compile.CodeCompiler;
import com.adapteach.codeassesser.compile.CompilationResult;
import com.adapteach.codeassesser.model.CompilationUnit;
import com.adapteach.codeassesser.model.Submission;
import com.adapteach.codeassesser.model.SubmissionResult;
import com.adapteach.codeassesser.model.Test;
import com.adapteach.codeassesser.run.CodeRunParams;
import com.adapteach.codeassesser.run.CodeRunner;

import java.util.List;

public class SubmissionVerifier {

    private static final String METHOD_NAME = "execute";

    private static final String IMPORTS
            = "import java.util.ArrayList;" +
            "import java.util.List;";

    /**
     * runTests
     */
    private static final String METHOD_TEMPLATE
            = "public List<String> " + METHOD_NAME + "() {" +
            "   List<String> failedTestMessages = new ArrayList<>();" +
            "   String testFailMessage;" +
            "   %s" +
            "   return failedTestMessages;" +
            "}";

    /**
     * testIndex
     */
    private static final String TEST_METHOD_CALL_TEMPLATE
            = "testFailMessage = test%d();" +
            "if (testFailMessage != null) {" +
            "   failedTestMessages.add(testFailMessage);" +
            "}";

    private CodeCompiler compiler = new CodeCompiler();
    private CodeRunner runner = new CodeRunner();
    private TestCodeBuilder testCodeBuilder = new TestCodeBuilder();

    public SubmissionResult verify(Submission submission) {
        CompilationResult preCompilation = compileWithoutTests(submission);
        if (preCompilation.isSuccess()) {
            return compileAndRunWithTests(submission);
        } else {
            return preCompilation.asSubmissionResult();
        }
    }

    private CompilationResult compileWithoutTests(Submission submission) {
        CompilationUnit compilationUnit = submission.getCompilationUnits().get(0);
        String className = compilationUnit.getName();
        String code = compilationUnit.getCode();
        return compiler.compile(className, code);
    }

    private SubmissionResult compileAndRunWithTests(Submission submission) {
        CompilationResult compilation = compileWithTests(submission);
        if (compilation.isSuccess()) {
            return run(compilation);
        } else {
            return compilation.asSubmissionResult();
        }
    }

    private CompilationResult compileWithTests(Submission submission) {
        CompilationUnit compilationUnit = submission.getCompilationUnits().get(0);
        String className = compilationUnit.getName();
        String code = formatCodeWithTests(submission);
        return compiler.compile(className, code);
    }

    private SubmissionResult run(CompilationResult compilationResult) {
        CodeRunParams runParams = new CodeRunParams();
        runParams.setCompilationResult(compilationResult);
        runParams.setMethodName(METHOD_NAME);
        return runner.run(runParams).asSubmissionResult();
    }

    private String formatCodeWithTests(Submission submission) {
        StringBuilder code = new StringBuilder(IMPORTS + submission.getCompilationUnits().get(0).getCode());
        code.deleteCharAt(code.lastIndexOf("}"));

        List<Test> tests = submission.getAssessment().getTests();
        appendTestMethods(tests, code);

        appendMethodToCall(tests.size(), code);

        code.append("}");
        return code.toString();
    }

    private void appendTestMethods(List<Test> tests, StringBuilder code) {
        for (int i = 0; i < tests.size(); i++) {
            Test test = tests.get(i);
            code.append(testCodeBuilder.buildTestCode(test, i));
        }
    }

    private void appendMethodToCall(int testCount, StringBuilder code) {
        StringBuilder testMethodCalls = new StringBuilder();
        for (int i = 0; i < testCount; i++) {
            testMethodCalls.append(String.format(TEST_METHOD_CALL_TEMPLATE, i));
        }

        String methodCode = String.format(METHOD_TEMPLATE, testMethodCalls.toString());
        code.append(methodCode);
    }

}
