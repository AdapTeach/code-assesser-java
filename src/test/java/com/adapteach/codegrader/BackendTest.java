package com.adapteach.codegrader;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class BackendTest {

    Backend backend = new Backend();

    @Test
    public void helloWorld() throws IOException {
        SubmissionJson submission = new SubmissionJson();

        submission.code = "public class Program {" +
                "" +
                "   public String execute() {" +
                "       return \"Hello, World !\";" +
                "   }" +
                "" +
                "}";

        ResultJson result = backend.run(submission);

        assertThat(result.out).isEqualTo("Hello, World !");
    }

    @Test
    public void meaningOfLife() throws IOException {
        SubmissionJson submission = new SubmissionJson();

        submission.code = "public class Program {" +
                "" +
                "   public String execute() {" +
                "       return \"42\";" +
                "   }" +
                "" +
                "}";

        ResultJson result = backend.run(submission);

        assertThat(result.out).isEqualTo("42");
    }

}
