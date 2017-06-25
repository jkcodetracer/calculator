package com.example.codetracer.calculator;

/**
 * Created by codetracer on 5/9/17.
 */

public class SinExp extends Exp{
    Exp exp;

    public SinExp(Exp e){
        exp = e;
    }

    @Override
    public double evaluate(Subs subs) {
        return Math.sin(exp.evaluate(subs));
    }

    @Override
    public String show() {
        return "sin(" + exp.show() + ")";
    }

    @Override
    public int size() {
        return 0;
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
