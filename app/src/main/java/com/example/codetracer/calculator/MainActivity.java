package com.example.codetracer.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends Activity {

    GridLayout 		gridLayout;
    TextView		expressionTextView;
    LinearLayout 	clearDelLinearLayout;

    String[] buttonTexts = new String[]
            {
                    "7", "8", "9", "/",
                    "4", "5", "6", "x",
                    "1", "2", "3", "-",
                    ".", "0", "=", "+"
            };

    private boolean isOperator(char ch) {
        boolean bOperator = false;
        if (ch == '+' || ch == '-' || ch == 'x' || ch == 'X' || ch == '/')
            bOperator = true;

        return bOperator;
    }

    private boolean appendDotValid(String exp) {
        if (exp.equals(""))
            return true;

        int expLen = exp.length();
        for (int ii = expLen - 1; ii >= 0; --ii) {
            char ch = exp.charAt(ii);
            if (isOperator(ch))
                return true;
            else if (ch == '.')
                return false;
        }

        return true;
    }

    private ArrayList<String> getRPolish(String expression) {
        int i = 0;
        Stack<Character> stack = new Stack<Character>();
        String number = new String();
        ArrayList<String> list = new ArrayList<String>();
        Log.i("!!!!!", expression);
        for (i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c) {
                case '+':
                case '-':
                    if (!number.isEmpty()) {
                        list.add(number);
                        number = "";
                    }
                    while(!stack.isEmpty()) {
                        list.add(String.valueOf(stack.pop()));
                    }
                    stack.push(c);
                    break;
                case 'x':
                case '/':
                    if (!number.isEmpty()) {
                        list.add(number);
                        number = "";
                    }
                    while(!stack.isEmpty() &&
                            (stack.peek() == '/' || stack.peek() == 'x')) {
                        list.add(String.valueOf(stack.pop()));
                    }
                    stack.push(c);
                    break;
                default:
                    number = number + String.valueOf(c);
                    break;
            }
        }

        if(!number.isEmpty()) {
            list.add(number);
        }

        while(!stack.isEmpty()) {
            list.add(String.valueOf(stack.pop()));
        }

        return list;
    }

    private double rPolishEval(ArrayList<String> rPolishExp) {
        int i = 0;
        double result = 0.0f, numA, numB;
        String tmp;
        Stack<String> stack = new Stack<String>();

        for (i = 0;i < rPolishExp.size(); i++){
            tmp = rPolishExp.get(i);
            Log.i("tmp", tmp);
            if (Character.isDigit(tmp.charAt(0))) {
                stack.push(tmp);
            } else {
                numA = Double.parseDouble(stack.pop());
                numB = Double.parseDouble(stack.pop());
                switch (tmp.charAt(0)) {
                    case '+':
                        result = numA + numB;
                        break;
                    case '-':
                        result = numB - numA;
                        break;
                    case 'x':
                        result = numA * numB;
                        break;
                    case '/':
                        result = numB/numA;
                        break;
                    default:
                        result = 9999999;
                        break;
                }
                stack.push(String.valueOf(result));
            }
        }

        return Double.parseDouble(stack.pop());
    }

    public double getResult(String expression){
        double result = 0.0f;
        ArrayList<String> rPolishExp;

        rPolishExp = getRPolish(expression);

        result = rPolishEval(rPolishExp);

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int	screenWidth = size.x;
        int oneQuarterWidth = (int) (screenWidth * 0.25);

        gridLayout = (GridLayout)findViewById(R.id.root);

        for (int ii = 0; ii < buttonTexts.length; ii++)
        {
            Button btn = new Button(this);
            btn.setText(buttonTexts[ii]);
            btn.setTextSize(40);

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Button bn = (Button)arg0;
                    String bnText = bn.getText().toString();

                    TextView expressionTextView = (TextView)findViewById(R.id.expressionTextView);
                    String oldExpression = expressionTextView.getText().toString();

                    char inputCh = bnText.charAt(bnText.length()-1);
                    if (isOperator(inputCh)) {
                        if (oldExpression.equals(""))
                            return;

                        char lastCh = oldExpression.charAt(oldExpression.length()-1);
                        if (isOperator(lastCh))
                            return;
                    }

                    if (inputCh == '.' && !appendDotValid(oldExpression)) {
                        return;
                    }

                    String newExpression = null;
                    if (bnText.equals("=")) {
                        double result = getResult(oldExpression);
                        newExpression = Double.toString(result);
                    }
                    else {
                        newExpression = oldExpression.concat(bnText);
                    }

                    expressionTextView.setText(newExpression);
                }
            });

            GridLayout.Spec rowSpec = GridLayout.spec(ii/4 + 2);
            GridLayout.Spec columnSpec = GridLayout.spec(ii % 4 );
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.width = oneQuarterWidth;
            gridLayout.addView(btn, params);
        }
    }

    public void onClearText(View v) {
        TextView expressionTextView = (TextView)findViewById(R.id.expressionTextView);
        expressionTextView.setText("");
    }

    public void onDeleteText(View v) {
        TextView expressionTextView = (TextView)findViewById(R.id.expressionTextView);
        String oldExp = expressionTextView.getText().toString().trim();
        if (oldExp.equals(""))
            return;

        // Remove the last character.
        oldExp = oldExp.substring(0, oldExp.length()-1);
        expressionTextView.setText(oldExp);
    }

}