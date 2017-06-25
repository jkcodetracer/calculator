package com.example.codetracer.calculator;

/**
 * Created by codetracer on 5/2/17.
 */
public class LitExp extends Exp{
    Exp exp;
    double value;

    public LitExp(double v) {
        super();
        value = v;
    }

    @Override
    public double evaluate(Subs subs) {
        return value;
    }

    @Override
    public String show() {
        return String.valueOf(value);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public int height() {
        return 0;
    }
    @Override
    public int operators() {
        return 0;
    }
}
