package com.adapteach.codeassesser.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Test {

    private String title;

    private String initializationCode;

    private List<String> expectations = new ArrayList<>();

}
