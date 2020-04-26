package com.example.restservice;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Foo implements Cloneable {
    static int bar = 100;
    public Map<String, Integer> m = new TreeMap<>();
    public Map<String, String> hm = new HashMap<>();

    Foo() {
        m.put("A", 1);
        m.put("B", 2);

        System.out.println("Foo constructor, bar: " + this.bar);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
