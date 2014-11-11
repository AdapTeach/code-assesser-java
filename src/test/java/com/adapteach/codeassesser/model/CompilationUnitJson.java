package com.adapteach.codeassesser.model;

import com.google.api.client.util.Key;
import com.google.api.client.util.Value;

public class CompilationUnitJson {

    public enum Kind {
        @Value CLASS,
        @Value INTERFACE,
        @Value ENUM
    }

    @Key
    public Kind kind = Kind.CLASS;

    @Key
    public String name;

    @Key
    public String code;

}
