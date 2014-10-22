package com.adapteach.codegrader;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {

    private String code = null;

    public InMemoryJavaFileObject(String className, String code) throws URISyntaxException {
        super(new URI(className), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }

}