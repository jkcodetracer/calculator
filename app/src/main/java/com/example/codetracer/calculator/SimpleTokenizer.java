package com.example.codetracer.calculator;
/*
 * SimpleTokenizer
 * Eric McCreath 2017
 */

import android.util.Log;

public class SimpleTokenizer extends Tokenizer {

	String text; // the text to be tokenizer
	int pos; // position the tokenizer is currently pointing at.
	Object current; // the current token that has just been tokenized, noting
					// the "pos" will point just after the text of this token

	String symbols = "()?:";
	char[] myspace = {' ', '\t', '\n'};

	public SimpleTokenizer(String text) {
		this.text = text;
		pos = 0;
		next();
	}

	@Override
	boolean hasNext() {
		return current != null;
	}

	@Override
	Object current() {
		return current;
	}

	@Override
	void next() {
		int i = 0, oldpos = 0;
		char c = 0;
		int textlen = text.length();

		
		if (pos < textlen)
			c = text.charAt(pos);

		// consume the white space
		while (pos < textlen) {
            c = text.charAt(pos);
            oldpos = pos;
            for (i = 0; i < 3; i++) {
                if (c == myspace[i]) {
                    pos++;
                    break;
                }
            }

            if (oldpos == pos)
                break;
        }
		// modify this code

		if (pos >= textlen) {
			current = null;
			return;
		} else if (c == '(') {
			pos++;
			current = "(";
			return;
		} else if (c == ')') {
		    pos++;
		    current = ")";
		    return;
        } else if (c == '*') {
		    pos++;
		    current = "*";
		    return;
        } else if (c == '/') {
		    pos ++;
		    current = "/";
		    return;
        } else if (c == ',') {
			pos ++;
			current = ",";
			return;
		} else if (c == '-') {
			pos ++;
			current = "-";
			return;
		} else if (c == '+') {
            pos ++;
            current = "+";
            return;
        } else if (Character.isDigit(c)) {
			String res = "" + c;
			pos++;
			if (pos >= textlen) {
				current = Double.valueOf(res);
				return;
			}
			c = text.charAt(pos);
			while (pos < textlen && (c == '.' || Character.isDigit(c))) {
				res += c;
				pos++;
				if (pos >= textlen)
					break;

				c = text.charAt(pos);
			}
			current = Double.valueOf(res);
			return;
		} else if (Character.isAlphabetic(c)){
			String res = "" + c;
			pos++;
			if (pos >= textlen) {
				current = res;
				return;
			}
			c = text.charAt(pos);
			while (pos < textlen && Character.isAlphabetic(c)) {
				res += c;
				pos++;
				if (pos >= textlen)
					break;

				c = text.charAt(pos);
			}
			current = res;
			return;
		}
	}

}
