package com.adapteach.codegrader.model;

import com.google.api.client.util.Key;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class TestJson {

    @Key
    public String title;

    @Key
    public String initializationCode;

    @Key
    public List<String> expectations = new ArrayList<>();

}
