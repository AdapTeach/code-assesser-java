package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.compile.CodeCompiler;
import com.adapteach.codeassesser.compile.CompilationResult;
import com.adapteach.codeassesser.model.CompilationUnit;
import com.adapteach.codeassesser.model.Submission;
import com.adapteach.codeassesser.model.SubmissionResult;
import com.adapteach.codeassesser.run.CodeRunParams;
import com.adapteach.codeassesser.run.CodeRunner;

import java.util.List;

public class SubmissionVerifier {

    private CodeCompiler compiler = new CodeCompiler();
    private CodeRunner runner = new CodeRunner();
    private TestClassCodeBuilder testCodeBuilder = new TestClassCodeBuilder();

    public SubmissionResult verify(Submission submission) {
        CompilationResult preCompilation = compileWithoutTests(submission);
        if (preCompilation.isSuccess()) {
            return compileAndRunWithTests(submission);
        } else {
            return preCompilation.asSubmissionResult();
        }
    }

    private CompilationResult compileWithoutTests(Submission submission) {
        return compiler.compile(submission.getAllCompilationUnits());
    }

    private SubmissionResult compileAndRunWithTests(Submission submission) {
        CompilationResult compilation = compileWithTests(submission);
        if (compilation.isSuccess()) {
            return run(compilation);
        } else {
            return compilation.asSubmissionResult();
        }
    }

    private CompilationResult compileWithTests(Submission submission) {
        List<CompilationUnit> compilationUnits = submission.getAllCompilationUnits();
        CompilationUnit testClass = testCodeBuilder.build(submission.getAssessment());
        compilationUnits.add(testClass);
        return compiler.compile(compilationUnits);
    }

    private SubmissionResult run(CompilationResult compilationResult) {
        CodeRunParams runParams = new CodeRunParams();
        runParams.setCompilationResult(compilationResult);
        runParams.setMethodName(TestClassCodeBuilder.EXECUTE);
        return runner.run(runParams).asSubmissionResult();
    }

}
