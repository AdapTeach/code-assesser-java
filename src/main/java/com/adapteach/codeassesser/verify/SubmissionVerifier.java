package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.compile.*;
import com.adapteach.codeassesser.run.CodeRunParams;
import com.adapteach.codeassesser.run.CodeRunner;

import java.util.List;
import java.util.stream.Collectors;

public class SubmissionVerifier {

    private CodeCompiler compiler = new CodeCompiler();
    private CodeRunner runner = new CodeRunner();
    private TestClassCodeBuilder testCodeBuilder = new TestClassCodeBuilder();

    public SubmissionResult verify(Submission submission) {
        CompilationCheck compilation = compiler.checkCanCompile(submission.getAllCompilationUnits());
        if (compilation.passed()) {
            return compileWithTestsAndRun(submission);
        } else {
            return compilation.asSubmissionResult();
        }
    }

    private SubmissionResult compileWithTestsAndRun(Submission submission) {
        CompilationResult compilation;
        try {
            compilation = compileWithTests(submission);
        } catch (CodeStyleException e) {
            SubmissionResult result = new SubmissionResult();
            result.setPass(false);
            result.getCompilationErrors().add(e.getMessage());
            return result;
        }
        if (compilation.passed()) {
            return run(compilation);
        } else {
            return compilation.asSubmissionResult();
        }
    }

    private CompilationResult compileWithTests(Submission submission) {
        List<CompilationUnit> unsafeCompilationUnits = submission.getAllCompilationUnits();
        List<CompilationUnit> safeCompilationUnits = protectAgainstInfiniteLoops(unsafeCompilationUnits);
        CompilationUnit testClass = testCodeBuilder.build(submission.getAssessment());
        safeCompilationUnits.add(testClass);
        return compiler.compile(safeCompilationUnits);
    }

    private SubmissionResult run(CompilationResult compilationResult) {
        CodeRunParams runParams = new CodeRunParams();
        runParams.setCompilationResult(compilationResult);
        runParams.setMethodName(TestClassCodeBuilder.EXECUTE);
        return runner.run(runParams).asSubmissionResult();
    }

    private List<CompilationUnit> protectAgainstInfiniteLoops(List<CompilationUnit> unsafeCompilationUnits) {
        return unsafeCompilationUnits.stream().map(CompilationUnit::safeCopy).collect(Collectors.toList());
    }

}
