package com.example.codetracer.calculator;

/**
 * Created by codetracer on 5/2/17.
 */
public class VarExp extends Exp{
    String str;

    public VarExp(String value){
        super();
        str = value;
    }

    @Override
    public double evaluate(Subs subs) {
        return Integer.valueOf(subs.get(str));
    }

    @Override
    public String show() {
        return str;
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
