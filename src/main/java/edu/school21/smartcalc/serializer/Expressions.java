package edu.school21.smartcalc.serializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;

public class Expressions implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final LinkedHashSet<String> expressions = new LinkedHashSet<>();

    public LinkedHashSet<String> getExpressions() {
        return expressions;
    }

    public void addExpression(String expression) {
        this.expressions.add(expression);
    }

    public void clearExpressions() {
        this.expressions.clear();
    }

}
