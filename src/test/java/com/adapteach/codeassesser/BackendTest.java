package com.adapteach.codeassesser;

import com.adapteach.codeassesser.model.Assessments;
import com.adapteach.codeassesser.model.CompilationUnitJson;
import com.adapteach.codeassesser.model.SubmissionJson;
import com.adapteach.codeassesser.model.SubmissionResultJson;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class BackendTest {

    Backend backend = new Backend();

    @Test
    public void shouldPassForCorrectHelloWorldSubmission() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code
                = "public class " + helloWorldClass.name + " {" +
                "" +
                "   public String helloWorld() {" +
                "       return \"Hello, World !\";" +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isTrue();
    }

    @Test
    public void shouldFailForIncorrectHelloWorldSubmission() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code
                = "public class " + helloWorldClass.name + " {" +
                "" +
                "   public String helloWorld() {" +
                "       return \"Unexpected output\";" +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldRespondWithFailedTestMessages() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code
                = "public class " + helloWorldClass.name + " {" +
                "" +
                "   public String helloWorld() {" +
                "       return \"Unexpected output\";" +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.failedTestMessages).hasSize(1);
        assertThat(result.failedTestMessages.get(0)).contains("Failed");
    }

    @Test
    public void shouldFailWhenCompilationError() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code = "class { }"; // Can't compile this !

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldRespondWithCompilationErrors() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code = "class { }"; // Can't compile this !

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.compilationErrors).isNotEmpty();
        assertThat(result.compilationErrors.get(0)).contains(helloWorldClass.code);
    }

    @Test
    public void shouldFailWhenTestsCantCompileWithSubmission() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code
                = "public class " + helloWorldClass.name + " {" +
                "" +
                "   public String wrongMethodName() {" + // Wrong method name !
                "       return \"Hello, World !\";" +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldSupportAssessmentWithTestInitializationCode() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.allPositive();

        CompilationUnitJson allPositiveClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(allPositiveClass);
        allPositiveClass.name = "AllPositive";
        allPositiveClass.code
                = "public class " + allPositiveClass.name + " {" +
                "" +
                "   public boolean allPositive(int[] array) {" +
                "       for (int element : array) {" +
                "           if (element < 0) return false;" +
                "       }" +
                "       return true;" +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isTrue();
    }

    @Test
    public void shouldFailWhenException() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.initializedField();

        CompilationUnitJson initializedFieldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(initializedFieldClass);
        initializedFieldClass.name = "InitializedField";
        initializedFieldClass.code
                = "public class " + initializedFieldClass.name + " {" +
                "" +
                "   public String name;" + // Field is not initialized => NullPointerException will be thrown
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldRespondWithExceptionMessage() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.initializedField();

        CompilationUnitJson initializedFieldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(initializedFieldClass);
        initializedFieldClass.name = "Person";
        initializedFieldClass.code
                = "public class " + initializedFieldClass.name + " {" +
                "" +
                "   public String name;" + // Field is not initialized => NullPointerException will be thrown
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.exceptionMessage).contains("NullPointerException");
    }

    @Test
    public void shouldCompileProvidedClass() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.inheritance();

        CompilationUnitJson childClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(childClass);
        childClass.name = "Child";
        childClass.code = "public class Child extends Parent {}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isTrue();
    }

    @Test
    public void shouldRespondWithCompilationErrorWhenCompilationUnitToSubmitIsMissing() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.inheritance();

        CompilationUnitJson childClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(childClass);
        childClass.name = "Child";
        childClass.code = "";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
        assertThat(result.compilationErrors).hasSize(1);
        assertThat(result.compilationErrors.get(0)).contains("Missing").contains("Child").contains("class");
    }

    @Test
    public void shouldFailGracefullyWhenSubmittingInfiniteWhileLoop() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson infiniteLoop = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(infiniteLoop);
        infiniteLoop.name = "HelloWorld";
        infiniteLoop.code = "class HelloWorld { String helloWorld() { while(true) {} } }";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldFailGracefullyWhenSubmittingInfiniteWhileLoopWithReturn() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson infiniteLoop = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(infiniteLoop);
        infiniteLoop.name = "HelloWorld";
        infiniteLoop.code = "class HelloWorld { String helloWorld() { while(true) {} return \"\"; } }";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldFailGracefullyWhenSubmittingInfiniteWhileLoopWithoutBraces() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson infiniteLoop = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(infiniteLoop);
        infiniteLoop.name = "HelloWorld";
        infiniteLoop.code = "class HelloWorld { String helloWorld() { while(true); } }";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldFailGracefullyWhenSubmittingInfiniteWhileLoopWithoutBracesWithReturn() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson infiniteLoop = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(infiniteLoop);
        infiniteLoop.name = "HelloWorld";
        infiniteLoop.code = "class HelloWorld { String helloWorld() { while(true); return \"\"; } }";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldFailGracefullyWhenSubmittingInfiniteForLoop() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson infiniteLoop = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(infiniteLoop);
        infiniteLoop.name = "HelloWorld";
        infiniteLoop.code = "class HelloWorld { String helloWorld() { for(;;) {} } }";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldFailGracefullyWhenSubmittingInfiniteForLoopWithReturn() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson infiniteLoop = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(infiniteLoop);
        infiniteLoop.name = "HelloWorld";
        infiniteLoop.code = "class HelloWorld { String helloWorld() { for(;;) {} return \"\"; } }";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldInterruptThreadWhenSubmittingInfiniteWhileLoop() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code
                = "public class " + helloWorldClass.name + " {" +
                "" +
                "   public String helloWorld() {" +
                "       while (true) {" +
                "           if (Thread.currentThread().isInterrupted() && !Thread.currentThread().isInterrupted())" +
                "               break;" +
                "           System.out.println(\"Infinite Loop\");" +
                "       }" +
                "       return \"Trying to kill the server with infinite loop\";  " +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
        assertThat(result.exceptionMessage).contains("too long");
    }

    @Test
    public void shouldInterruptThreadWhenSubmittingInfiniteForLoop() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        CompilationUnitJson helloWorldClass = new CompilationUnitJson();
        submission.submittedCompilationUnits.add(helloWorldClass);
        helloWorldClass.name = "HelloWorld";
        helloWorldClass.code
                = "public class " + helloWorldClass.name + " {" +
                "" +
                "   public String helloWorld() {" +
                "       for (;;) {" +
                "           if (Thread.currentThread().isInterrupted() && !Thread.currentThread().isInterrupted())" +
                "               break;" +
                "           System.out.println(\"Infinite Loop\");" +
                "       }" +
                "       return \"Trying to kill the server with infinite loop\";  " +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
        assertThat(result.exceptionMessage).contains("too long");
    }

}
