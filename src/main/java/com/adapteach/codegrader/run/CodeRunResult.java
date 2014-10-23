package com.adapteach.codegrader.run;

import lombok.Data;

import java.io.StringWriter;
import java.io.Writer;

@Data
public class CodeRunResult {

    private boolean pass;

    private Writer out = new StringWriter();

    private String err;

}
