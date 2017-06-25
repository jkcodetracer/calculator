package com.example.codetracer.calculator;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by codetracer on 5/23/17.
 */

public class CalcTest {
    static String e1 = "2*(3+5)";

    @Test
    public void evaluationTest() throws Exception{
        Tokenizer t = new SimpleTokenizer(e1);
        Exp pe = Exp.parseExp(t);
        Subs subs = new Subs();

        assertEquals(pe.evaluate(subs), 16.0, 0.001);
    }

    @Test
    public void showTest() throws Exception{
        Tokenizer t = new SimpleTokenizer(e1);
        Exp pe = Exp.parseExp(t);
        Subs subs = new Subs();

        assertEquals(pe.show(), "( 2.0 * ( 3.0 + 5.0 ) )");
    }
}
