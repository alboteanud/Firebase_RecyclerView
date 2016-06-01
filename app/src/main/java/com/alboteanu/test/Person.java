package com.alboteanu.test;

/**
 * Created by Dan on 23/05/2016.
 */
public class Person {
    public String name;
    public String detail_1;

    public Person() {
    }

    public Person(String name, String detail_1) {
        this.name = name;
        this.detail_1 = detail_1;
    }

    public String getName() {
        return name;
    }

    public String getDetail_1() {
        return detail_1;
    }

    @Override
    public String toString() {
        return name;
    }
}
