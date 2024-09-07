package edu.school21.smartcalc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void performCalculation() {
        Model model = new Model();
        assertEquals(model.performCalculation("4+4", "0"), "8.000000");
    }

    @Test
    void performCalculationWithX() {
        Model model = new Model();
        assertEquals(model.performCalculation("4+x", "4"), "8.000000");
    }
}