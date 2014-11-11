package com.adapteach.codeassesser.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Assessment {

    private List<CompilationUnit> providedCompilationUnits = new ArrayList<>();

    private List<Test> tests = new ArrayList<>();

}
