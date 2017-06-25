package com.example.codetracer.calculator;

import java.util.ArrayList;

/*
 * FunCallExp 
 * Eric McCreath 2017
 */
public class FunCallExp extends Exp {

	String funname;
	ArrayList<Exp> exps;
	
	public FunCallExp(String name, ArrayList<Exp> es) {
		funname = name;
		exps = es;
		
	}
	
	public FunCallExp(String name, Exp e1) {
		funname = name;
		exps = new ArrayList<Exp>();
		exps.add(e1);
		
	}
	
	public FunCallExp(String name, Exp e1, Exp e2) {
		funname = name;
		exps = new ArrayList<Exp>();
		exps.add(e1);
		exps.add(e2);
	}

	@Override
	public double evaluate(Subs subs) {
		return 0;
	}

	@Override
	public String show() {
		String res =  funname + "(";
		for (int i=0;i< exps.size();i++) {
			res += exps.get(i).show();
			if (i < exps.size()-1) res += ",";
		}
		return res + ")";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return show();
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public int height() {
	    return 1;
	}

	@Override
	public int operators() {
	    return 1;
	}
}
