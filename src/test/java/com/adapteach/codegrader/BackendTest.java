package com.adapteach.codegrader;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class BackendTest {

    Backend backend = new Backend();

    @Test
    public void helloWorld() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("public class Program {");
        sb.append("");
        sb.append("public String execute() {");
        sb.append("return \"Hello, World !\";");
        sb.append("}");
        sb.append("");
        sb.append("}");
        SubmissionJson submission = new SubmissionJson();
        submission.code = sb.toString();

        ResultJson result = backend.run(submission);

        assertThat(result.out).isEqualTo("Hello, World !");
    }

    @Test
    public void meaningOfLife() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("public class Program {");
        sb.append("");
        sb.append("public String execute() {");
        sb.append("return \"42\";");
        sb.append("}");
        sb.append("");
        sb.append("}");
        SubmissionJson submission = new SubmissionJson();
        submission.code = sb.toString();

        ResultJson result = backend.run(submission);

        assertThat(result.out).isEqualTo("42");
    }

}
