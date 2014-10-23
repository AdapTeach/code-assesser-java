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

    /**
     * testCount, testResults
     */
    private static final String METHOD_TEMPLATE
            = "public boolean " + METHOD_NAME + "() {" +
            "   boolean[] testResults = new boolean[%d];" +
            "   %s" +
            "   boolean pass = true;" +
            "   for (boolean result : testResults) {" +
            "       if (!result) pass = false;" +
            "   }" +
            "   return pass;" +
            "}";

    /**
     * testIndex, testIndex
     */
    private static final String TEST_METHOD_CALL_TEMPLATE = "testResults[%d] = test%d();";

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
            CompilationResult compilationResult = compile(submission);
            CodeRunResult runResult = run(compilationResult);
            submissionResult.setPass(runResult.isPass());
            return submissionResult;
        }

    }

    public CompilationResult compileSubmittedCode(Submission submission) {
        String className = submission.getAssessment().getClassName();
        String code = submission.getCode();
        return compiler.compile(className, code);
    }

    private CompilationResult compile(Submission submission) {
        String className = submission.getAssessment().getClassName();
        String code = formatRunnableCode(submission);
        return compiler.compile(className, code);
    }

    private CodeRunResult run(CompilationResult compilationResult) {
        CodeRunParams runParams = new CodeRunParams();
        runParams.setCompilationResult(compilationResult);
        runParams.setMethodName(METHOD_NAME);
        return runner.run(runParams);
    }

    private String formatRunnableCode(Submission submission) {
        StringBuilder code = new StringBuilder(submission.getCode());
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
            testMethodCalls.append(String.format(TEST_METHOD_CALL_TEMPLATE, i, i));
        }

        String methodCode = String.format(METHOD_TEMPLATE, testCount, testMethodCalls.toString());
        code.append(methodCode);
    }

}
