package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.model.Test;

public class TestCodeBuilder {

    /**
     * index, initializationCode, expectation, index, title
     */
    private static final String TEMPLATE
            = "public String test%d() {" +
            "   %s" +
            "   boolean passExpectation = (%s);" +
            "   if (!passExpectation) {" +
            "       return \"Failed test #%d : %s\";" +
            "   }" +
            "   return null;" +
            "}";

    public String buildTestCode(Test test, int index) {
        return String.format(TEMPLATE, index, test.getInitializationCode(), test.getExpectations().get(0), index, test.getTitle());
    }

}
