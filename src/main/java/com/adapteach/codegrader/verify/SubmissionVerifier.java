package com.adapteach.codegrader.verify;

import com.adapteach.codegrader.compile.CodeCompiler;
import com.adapteach.codegrader.compile.CompilationResult;
import com.adapteach.codegrader.model.Submission;
import com.adapteach.codegrader.model.SubmissionResult;
import com.adapteach.codegrader.model.Test;
import com.adapteach.codegrader.run.CodeRunParams;
import com.adapteach.codegrader.run.CodeRunResult;
import com.adapteach.codegrader.run.CodeRunner;

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
        SubmissionResult submissionResult = new SubmissionResult();

        // Check submitted code can compile without errors
        CompilationResult preCompilation = compileSubmittedCode(submission);
        if (!preCompilation.isSuccess()) {
            submissionResult.setPass(false);
            submissionResult.setCompilationErrors(preCompilation.getCompilationErrors());
            return submissionResult;
        } else { // Compile with tests and execute
            CompilationResult compilationResult = compileWithTests(submission);
            CodeRunResult runResult = run(compilationResult);
            submissionResult.setPass(runResult.isPass());
            submissionResult.setFailedTestMessages(runResult.getFailedTestMessages());
            return submissionResult;
        }

    }

    public CompilationResult compileSubmittedCode(Submission submission) {
        String className = submission.getAssessment().getClassName();
        String code = submission.getCode();
        return compiler.compile(className, code);
    }

    private CompilationResult compileWithTests(Submission submission) {
        String className = submission.getAssessment().getClassName();
        String code = formatCodeWithTests(submission);
        return compiler.compile(className, code);
    }

    private CodeRunResult run(CompilationResult compilationResult) {
        CodeRunParams runParams = new CodeRunParams();
        runParams.setCompilationResult(compilationResult);
        runParams.setMethodName(METHOD_NAME);
        return runner.run(runParams);
    }

    private String formatCodeWithTests(Submission submission) {
        StringBuilder code = new StringBuilder(IMPORTS + submission.getCode());
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
