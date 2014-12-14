package com.adapteach.codeassesser.model;

public class Assessments {

    public static AssessmentJson helloWorld() {
        AssessmentJson helloWorld = new AssessmentJson();

        // TITLE : Hello World
        // INSTRUCTIONS : Write a static method called helloWorld which returns 'Hello, World !'
        // START CODE :
        //        public class HelloWorld {
        //            // CODE HERE
        //        }
        TestJson test = new TestJson();
        helloWorld.tests.add(test);
        test.title = "Should return 'Hello, World !'";
        test.assertions.add("new HelloWorld().helloWorld().equals(\"Hello, World !\")");

        return helloWorld;
    }

    public static AssessmentJson allPositive() {
        AssessmentJson allPositive = new AssessmentJson();

        // TITLE : Hello World
        // INSTRUCTIONS : Implement the static method allPositive() which takes an array of integers as input and returns true if all the elements in the array are positive, false otherwise
        // START CODE :
        //        public class AllPositive {
        //
        //            public boolean allPositive(int[] array) {
        //                // CODE HERE
        //            }
        //
        //        }

        TestJson test0 = new TestJson();
        allPositive.tests.add(test0);
        test0.title = "Should return true when passed an array containing positive integers only";
        test0.initializationCode = "int[] array = {1, 5, 3, 7, 4, 9, 2};";
        test0.assertions.add("new AllPositive().allPositive(array)");

        TestJson test1 = new TestJson();
        allPositive.tests.add(test1);
        test1.title = "Should return false when passed an array containing a mix of positive and negative integers";
        test1.initializationCode = "int[] array = {1, 5, 3, -7, 4, -9, 2};";
        test1.assertions.add("!(new AllPositive().allPositive(array))");

        return allPositive;
    }

    public static AssessmentJson initializedField() {
        AssessmentJson initializedField = new AssessmentJson();

        TestJson test0 = new TestJson();
        initializedField.tests.add(test0);
        test0.initializationCode = "Person person = new Person();";
        test0.assertions.add("person.name.equals(\"Bob\")");

        return initializedField;
    }

    public static AssessmentJson inheritance() {
        AssessmentJson inheritance = new AssessmentJson();

        CompilationUnitJson parentClass = new CompilationUnitJson();
        inheritance.providedCompilationUnits.add(parentClass);
        parentClass.name = "Parent";
        parentClass.code = "public class Parent {}";

        TestJson test0 = new TestJson();
        inheritance.tests.add(test0);
        test0.assertions.add("Parent.class.isAssignableFrom(Child.class)");

        return inheritance;
    }

}
