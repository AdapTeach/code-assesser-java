package com.adapteach.codeassesser.compile;

import com.adapteach.codeassesser.verify.SubmissionResult;
import com.google.common.base.Preconditions;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompilationCheck {

    private final List<String> compilationErrors;

    private final List<CompilationUnit> missingCompilationUnits;

    public CompilationCheck(MemoryClassLoader classLoader) {
        this.compilationErrors = classLoader.getCompilationErrors();
        this.missingCompilationUnits = classLoader.getMissingCompilationUnits();
    }

    public boolean passed() {
        return compilationErrors.isEmpty() && missingCompilationUnits.isEmpty();
    }

    public SubmissionResult asSubmissionResult() {
        Preconditions.checkState(!passed(), "This method should be called only for failing checks");
        final SubmissionResult result = new SubmissionResult();
        result.setPass(false);
        result.getCompilationErrors().addAll(compilationErrors);
        List<String> missingCompilationUnitMessages = missingCompilationUnits.stream().map(CompilationUnit::getMissingMessage).collect(Collectors.toList());
        result.getCompilationErrors().addAll(missingCompilationUnitMessages);
        return result;
    }
}
