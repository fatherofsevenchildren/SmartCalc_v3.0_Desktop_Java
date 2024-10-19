package edu.school21.smartcalc.model;

public class Model {

    static {
        System.load("/cpplib/libnative.dylib");
    }

    public native String performCalculation(String expression, String xExpression);
}
