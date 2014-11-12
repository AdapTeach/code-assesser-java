package com.adapteach.codeassesser.model;

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

    private List<CompilationUnit> compilationUnits = new ArrayList<>();

    public List<CompilationUnit> getAllCompilationUnits() {
        List<CompilationUnit> toReturn = new ArrayList<>();
        toReturn.addAll(compilationUnits);
        toReturn.addAll(assessment.getProvidedCompilationUnits());
        return toReturn;
    }

}
