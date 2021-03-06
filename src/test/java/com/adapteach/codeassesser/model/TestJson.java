package com.adapteach.codeassesser.model;

import com.google.api.client.util.Key;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class TestJson {

    @Key
    public String title = "";

    @Key
    public String initializationCode = "";

    @Key
    public List<String> assertions = new ArrayList<>();

}
