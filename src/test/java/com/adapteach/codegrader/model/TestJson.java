package com.adapteach.codegrader.model;

import com.google.api.client.util.Key;

import java.util.ArrayList;
import java.util.List;

public class TestJson {

    @Key
    public String title;

    @Key
    public String code;

    @Key
    public List<String> expectations = new ArrayList<>();

}
