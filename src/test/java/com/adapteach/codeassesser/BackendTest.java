package com.adapteach.codeassesser;

import com.adapteach.codeassesser.model.*;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class BackendTest {

    Backend backend = new Backend();

    @Test
    public void shouldPassForCorrectHelloWorldSubmission() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        submission.code
                = "public class " + submission.assessment.className + " {" +
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

        submission.code
                = "public class " + submission.assessment.className + " {" +
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

        submission.code
                = "public class " + submission.assessment.className + " {" +
                "" +
                "   public String helloWorld() {" +
                "       return \"Unexpected output\";" +
                "   }" +
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.failedTestMessages.size()).isEqualTo(1);
        assertThat(result.failedTestMessages.get(0)).contains("Failed");
    }

    @Test
    public void shouldFailWhenCompilationError() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        submission.code = "class { }"; // Can't compile this !

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.pass).isFalse();
    }

    @Test
    public void shouldRespondWithCompilationErrors() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        submission.code = "class { }"; // Can't compile this !

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.compilationErrors.size()).isEqualTo(1);
        assertThat(result.compilationErrors.get(0)).contains(submission.code);
    }

    @Test
    public void shouldFailWhenTestsCantCompileWithSubmission() throws IOException {
        SubmissionJson submission = new SubmissionJson();
        submission.assessment = Assessments.helloWorld();

        submission.code
                = "public class " + submission.assessment.className + " {" +
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

        submission.code
                = "public class " + submission.assessment.className + " {" +
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

        submission.code
                = "public class " + submission.assessment.className + " {" +
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


        submission.code
                = "public class " + submission.assessment.className + " {" +
                "" +
                "   public String name;" + // Field is not initialized => NullPointerException will be thrown
                "" +
                "}";

        SubmissionResultJson result = backend.submit(submission);

        assertThat(result.exceptionMessage).contains("NullPointerException");
    }

}
