package com.adapteach.codegrader.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Test {

    private String title;

    private String code;

    private List<String> expectations = new ArrayList<>();

}
