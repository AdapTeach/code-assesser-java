package com.adapteach.codegrader.verify;

import com.adapteach.codegrader.Console;
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

    public CodeRunner runner = new CodeRunner();
    private TestCodeBuilder testCodeBuilder = new TestCodeBuilder();

    public SubmissionResult verify(Submission submission) {

        CodeRunParams runParams = new CodeRunParams();
        runParams.setClassName(submission.getAssessment().getClassName());
        runParams.setMethodName(METHOD_NAME);
        runParams.setCode(formatRunnableCode(submission));

        CodeRunResult runResult = runner.run(runParams);

        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setPass(runResult.isPass());

        return submissionResult;
    }

    private String formatRunnableCode(Submission submission) {
        StringBuilder code = new StringBuilder(submission.getCode());
        code.deleteCharAt(code.lastIndexOf("}"));

        List<Test> tests = submission.getAssessment().getTests();
        appendTestMethods(tests, code);

        appendMethodToCall(tests.size(), code);

        code.append("}");
        Console.log(code);
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
