package com.adapteach.codeassesser.verify;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Test {

    private String title;

    private String initializationCode;

    private List<String> assertions = new ArrayList<>();

}
