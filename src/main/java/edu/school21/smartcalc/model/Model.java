package edu.school21.smartcalc.model;

public class Model {

    /*
        Здесь временно нужно вписать абсолютный путь до библиотеки(
     */

    static {
        System.load("/Users/leonidak/progects/APJ2_SmartCalc_v3.0_Desktop_Java-1/src/SmartCalc/src/main/resources/cpplib/libnative.dylib");
    }


    public native String performCalculation(String expression, String xExpression);
}
