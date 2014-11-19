package com.adapteach.codeassesser.verify;

import com.adapteach.codeassesser.compile.CompilationUnit;
import com.adapteach.codeassesser.verify.Test;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Assessment {

    private List<CompilationUnit> providedCompilationUnits = new ArrayList<>();

    private List<Test> tests = new ArrayList<>();

}
