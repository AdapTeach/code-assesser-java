package com.adapteach.codeassesser.model;

import lombok.Data;

@Data
public class CompilationUnit {

    public enum Kind {
        CLASS, INTERFACE, ENUM;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private Kind kind = Kind.CLASS;

    private String name;

    private String code;

}
