package com.adapteach.codegrader.run;

import lombok.Data;

import java.io.Writer;

@Data
public class CodeRunParams {

    private String code;

    private String className;

    private String methodName;

}
