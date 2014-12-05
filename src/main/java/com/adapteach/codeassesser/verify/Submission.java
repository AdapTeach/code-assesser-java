package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.compile.CompilationUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    private Assessment assessment;

    private List<CompilationUnit> submittedCompilationUnits = new ArrayList<>();

    public List<CompilationUnit> getAllCompilationUnits() {
        List<CompilationUnit> toReturn = new ArrayList<>();
        toReturn.addAll(submittedCompilationUnits);
        toReturn.addAll(assessment.getProvidedCompilationUnits());
        return toReturn;
    }

}
