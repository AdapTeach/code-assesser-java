package com.adapteach.codegrader.verify;

import com.adapteach.codegrader.model.Test;

public class TestCodeBuilder {

    /**
     * index, expectation, index, title
     */
    private static final String TEMPLATE
            = "public boolean test%d() {" +
            "   boolean passExpectation = (%s);" +
            "   if (!passExpectation) {" +
            "       System.out.println(\"Failed test #%d : %s\");" +
            "   }" +
            "   return passExpectation;" +
            "}";

    public String buildTestCode(Test test, int index) {
        return String.format(TEMPLATE, index, test.getExpectations().get(0), index, test.getTitle());
    }

}
