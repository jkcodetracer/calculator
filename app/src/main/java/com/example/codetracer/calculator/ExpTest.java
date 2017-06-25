package com.example.codetracer.calculator;

/**
 * Created by codetracer on 5/2/17.
 */
public class ExpTest {
    static String e1 = "2*(3+5)";
    static String e2 = "3+2-4";
    static String e3 = "60/(7-1)";
    static String e4 = "(60-6)/(7-1)";
    static String e5 = "(60*5)/(7-1)";
    static String e6 = "A+1+B";

    public static void test(String[] args) {
        Tokenizer t = new SimpleTokenizer(e1);
        Exp pe = Exp.parseExp(t);
        Subs subs = new Subs();

        System.out.println(pe.show() + " = " + pe.evaluate(subs));
        System.out.println("size :"+ " = " + pe.size());
        System.out.println("height :"+ " = " + pe.height());
        System.out.println("operators: "+ " = " + pe.operators());

        Tokenizer t2 = new SimpleTokenizer(e6);
        Exp pe2 = Exp.parseExp(t2);
        Subs mysubs = new Subs();

        mysubs.put("A", 6);
        mysubs.put("B", 2);
        System.out.println(pe2.show() + " = " + pe2.evaluate(mysubs));
    }
}
