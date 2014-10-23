package com.adapteach.codegrader.model;

public class Assessments {

    public static final AssessmentJson HELLO_WORLD = helloWorld();
    public static final AssessmentJson ALL_POSITIVE = allPositive();

    private static AssessmentJson helloWorld() {
        AssessmentJson helloWorld = new AssessmentJson();

        helloWorld.title = "Hello World";

        helloWorld.instructions = "Write a static method called helloWorld which returns 'Hello, World !'";

        helloWorld.className = "HelloWorld";

        helloWorld.startCode
                = "public class " + helloWorld.className + " {" +
                "" +
                "   // CODE HERE" +
                "" +
                "}";

        TestJson test = new TestJson();
        helloWorld.tests.add(test);
        test.title = "Should return 'Hello, World !'";
        test.code = "";
        test.expectations.add("helloWorld().equals(\"Hello, World !\")");

        return helloWorld;
    }

    private static AssessmentJson allPositive() {
        AssessmentJson allPositive = new AssessmentJson();

        allPositive.title = "All Positive";

        allPositive.instructions = "Implement the static method allPositive() which takes an array of integers as input and returns true if all the elements in the array are positive, false otherwise";

        allPositive.className = "AllPositive";

        allPositive.startCode
                = "public class " + allPositive.className + " {" +
                "" +
                "   static boolean allPositive(int[] array) {" +
                "       // CODE HERE" +
                "   }" +
                "" +
                "}";

        TestJson test0 = new TestJson();
        allPositive.tests.add(test0);
        test0.title = "Should return true when passed an array containing positive integers only";
        test0.code = "int[] array = {1, 5, 3, 7, 4, 9, 2};";
        test0.expectations.add("allPositive(array)");

        TestJson test1 = new TestJson();
        allPositive.tests.add(test1);
        test1.title = "Should return false when passed an array containing a mix of positive and negative integers";
        test1.code = "int[] array = {1, 5, 3, -7, 4, -9, 2};";
        test1.expectations.add("!allPositive(array)");

        return allPositive;
    }

}
