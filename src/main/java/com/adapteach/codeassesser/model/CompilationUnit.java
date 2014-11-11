package com.adapteach.codeassesser.model;

import lombok.Data;

@Data
public class CompilationUnit {

    public enum Kind {
        CLASS, INTERFACE, ENUM
    }

    private Kind kind;

    private String name;

    private String code;

}
