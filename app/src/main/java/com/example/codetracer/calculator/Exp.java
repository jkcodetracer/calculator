package com.example.codetracer.calculator;/*
 * Exp 
 * Eric McCreath 2017
 */

public abstract class Exp {
	public abstract double evaluate(Subs subs);

	public abstract String show();
	public abstract int size();
	public abstract int height();
	public abstract int operators();
	//public abstract Exp simplify(Subs subs);

	public static Exp lit(int v) {
		return new LitExp(v);
	}
	public static Exp var(String v) {
		return new VarExp(v);
	}

	/*
		expr —> term + expr | term - expr | term
		term —> factor * term | factor/term | factor
		factor—> digit ｜(expr) | sin(expr) | cos(expr)
	 */

	public static Exp parseExp(Tokenizer tok) {
		Exp term = parseTerm(tok);
		if ( tok.hasNext() && tok.current().equals("+")){
			tok.next();
			Exp exp = parseExp(tok);
			return new AddExp(term, exp);
		} else if (tok.hasNext() && tok.current().equals("-")){
			tok.next();
			Exp exp = parseExp(tok);
			return new MinusExp(term, exp);
		} else {
			return term;
		}
	}

	private static Exp parseTerm(Tokenizer tok){
		Exp fact = parseFactor(tok);
		if (tok.hasNext() && tok.current().equals("*")) {
			tok.next();
			Exp term = parseTerm(tok);
			return new MultExp(fact, term);
		} else if (tok.hasNext() && tok.current().equals("/")) {
			tok.next();
			Exp term = parseTerm(tok);
			return new DivExp(fact, term);
		} else {
			return fact;
		}
	}

	private static Exp parseFactor(Tokenizer tok) {
		if ( tok.hasNext() && tok.current() instanceof Double) {
			Exp exp = new LitExp((Double) tok.current());
			tok.next();
			return exp;
		} else if (tok.hasNext() && tok.current().equals("(")) {
				tok.next();
				Exp exp = parseExp(tok);
				tok.parse(")");
				return exp;
		} else if (tok.hasNext() && tok.current().equals("sin")){
			tok.next();
			tok.parse("(");
			Exp exp = parseExp(tok);
			tok.parse(")");
			return new SinExp(exp);
		} else {
			Exp exp = new VarExp((String) tok.current());
			tok.next();
			return exp;
		}
	}

	public static Functions parseFunctions(Tokenizer tok) {
		return null;
	}
}
