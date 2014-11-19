package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.compile.CompilationUnit;

import java.util.List;

public class TestClassCodeBuilder {

    public static final String TEST_CLASS = "TestClass";

    public static final String EXECUTE = "execute";

    /**
     * testMethods, executeMethod
     */
    private static final String TEMPLATE
            = "import java.util.ArrayList;" +
            "import java.util.List;" +
            "" +
            "public class " + TEST_CLASS + " {" +
            "" +
            "   %s" +
            "" +
            "   %s" +
            "" +
            "}";

    /**
     * runTests
     */
    private static final String EXECUTE_METHOD_TEMPLATE
            = "public List<String> " + EXECUTE + "() {" +
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

    /**
     * index, initializationCode, expectation, index, title
     */
    private static final String TEST_METHOD_TEMPLATE
            = "public String test%d() {" +
            "   %s" +
            "   boolean passExpectation = (%s);" +
            "   if (!passExpectation) {" +
            "       return \"Failed test #%d : %s\";" +
            "   }" +
            "   return null;" +
            "}";


    public CompilationUnit build(Assessment assessment) {
        CompilationUnit testClass = new CompilationUnit();
        testClass.setName(TEST_CLASS);
        testClass.setCode(formatCode(assessment));
        return testClass;
    }

    private String formatCode(Assessment assessment) {
        List<Test> tests = assessment.getTests();
        String testMethods = formatTestMethods(tests);
        String executeMethod = formatExecuteMethod(tests.size());
        return String.format(TEMPLATE, testMethods, executeMethod);
    }

    private String formatTestMethods(List<Test> tests) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < tests.size(); i++) {
            Test test = tests.get(i);
            code.append(formatTestMethod(test, i));
        }
        return code.toString();
    }

    private String formatTestMethod(Test test, int index) {
        return String.format(TEST_METHOD_TEMPLATE, index, test.getInitializationCode(), test.getExpectations().get(0), index, test.getTitle());
    }

    private String formatExecuteMethod(int testCount) {
        StringBuilder testMethodCalls = new StringBuilder();
        for (int i = 0; i < testCount; i++) {
            testMethodCalls.append(String.format(TEST_METHOD_CALL_TEMPLATE, i));
        }
        return String.format(EXECUTE_METHOD_TEMPLATE, testMethodCalls.toString());
    }

}
