package com.adapteach.codeassesser.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Assessment {

    private String title;

    private String instructions;

    private String className;

    private String startCode;

    private List<Test> tests = new ArrayList<>();


}
