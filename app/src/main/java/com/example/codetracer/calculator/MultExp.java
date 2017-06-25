package com.example.codetracer.calculator;

/**
 * Created by codetracer on 5/2/17.
 */
public class MultExp extends Exp{
    Exp expA;
    Exp expB;
    public MultExp(Exp exp1, Exp exp2) {
        super();
        expA = exp1;
        expB = exp2;
    }
    @Override
    public double evaluate(Subs subs) {
        return expA.evaluate(subs) * expB.evaluate(subs);
    }

    @Override
    public String show() {
        return "( "+ expA.show() + " * " + expB.show() + " )";
    }

    @Override
    public int size() {
        return expA.size() + expB.size() + 1;
    }

    @Override
    public int height() {
        int lheight, rheight;
        lheight = expA.height();
        rheight = expB.height();
        if (lheight > rheight) {
            return lheight + 1;
        } else {
            return rheight + 1;
        }
    }

    @Override
    public int operators() {
        return expA.operators() + expB.operators() + 1;
    }
}

