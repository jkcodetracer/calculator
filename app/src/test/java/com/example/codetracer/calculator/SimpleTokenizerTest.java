package com.example.codetracer.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by codetracer on 5/23/17.
 */
public class SimpleTokenizerTest {
    static String e1 = "2*(3+5)";

    @Test
    public void nullTest() throws Exception {
        String empty = "";
        Tokenizer t = new SimpleTokenizer(empty);

        assertEquals(t.current(), null);
    }

    @Test
    public void operatorTest() throws Exception{
        String opt = "+-*/()";
        Tokenizer t = new SimpleTokenizer(opt);

        for (int i = 0; i < opt.length(); i++) {
            assertEquals(t.current(), String.valueOf(opt.charAt(i)));
            assertTrue(t.hasNext());
            t.next();
        }
    }

    @Test
    public void numTest() throws Exception{
        String num = "1400";
        Tokenizer t = new SimpleTokenizer(num);

        assertEquals(t.current(), 1400.0);
    }

    @Test
    public void symbleTest() throws Exception{
        String symble = "sin";
        Tokenizer t = new SimpleTokenizer(symble);

        assertEquals(t.current(), symble);
    }

    @Test
    public void equitionTest() throws Exception{
        String eq = "2*(13+50)";
        Tokenizer t = new SimpleTokenizer(eq);

        assertEquals(t.current(), 2.0);
        assertTrue(t.hasNext());
        t.next();

        assertEquals(t.current(), "*");
        assertTrue(t.hasNext());
        t.next();

        assertEquals(t.current(), "(");
        assertTrue(t.hasNext());
        t.next();

        assertEquals(t.current(), 13.0);
        assertTrue(t.hasNext());
        t.next();

        assertEquals(t.current(), "+");
        assertTrue(t.hasNext());
        t.next();

        assertEquals(t.current(), 50.0);
        assertTrue(t.hasNext());
        t.next();


        assertEquals(t.current(), ")");
        assertTrue(t.hasNext());
        t.next();
    }
}