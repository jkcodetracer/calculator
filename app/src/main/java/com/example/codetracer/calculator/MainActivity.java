package com.example.codetracer.calculator;

import android.net.Uri;
import android.os.Bundle;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String fileName = "replay.xml";
    GridLayout gridLayout;
    TextView expressionTextView;
    LinearLayout clearDelLinearLayout;
    Stack<String> replayList;

    String[] buttonTexts = new String[]
            {
                    "7", "8", "9", "/",
                    "4", "5", "6", "*",
                    "1", "2", "3", "-",
                    ".", "0", "=", "+"
            };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private boolean isOperator(char ch) {
        boolean bOperator = false;
        if (ch == '+' || ch == '-' || ch == '*' || ch == 'X' || ch == '/')
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
                    while (!stack.isEmpty()) {
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
                    while (!stack.isEmpty() &&
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

        if (!number.isEmpty()) {
            list.add(number);
        }

        while (!stack.isEmpty()) {
            list.add(String.valueOf(stack.pop()));
        }

        return list;
    }

    private double rPolishEval(ArrayList<String> rPolishExp) {
        int i = 0;
        double result = 0.0f, numA, numB;
        String tmp;
        Stack<String> stack = new Stack<String>();

        for (i = 0; i < rPolishExp.size(); i++) {
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
                        result = numB / numA;
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

    public double getResult(String expression) {
        double result = 0.0f;
        ArrayList<String> rPolishExp;
        Tokenizer t = new SimpleTokenizer(expression);
        Subs subs = new Subs();
        Exp pe = Exp.parseExp(t);
/**
        rPolishExp = getRPolish(expression);

        result = rPolishEval(rPolishExp);
**/

        try {
            result = pe.evaluate(subs);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Invalid expression", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.replay) {
            Log.d("replay expression ", "  ");
            TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
            if (!replayList.isEmpty())
                expressionTextView.setText(replayList.pop());
        }

        //showList();
        return super.onOptionsItemSelected(item);
    }

    protected void saveData() {
        Log.d("In save", "Data");
        LoadStore.save(getApplicationContext(), fileName, replayList);
    }

    protected void showList() {
        listView = (ListView) findViewById(R.id.listView);
        String [] arrayData = {"", ""};
        for (String i:replayList) {
            arrayData[0] = i;
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, replayList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replayList = new Stack<String>();
        replayList = LoadStore.load(getApplicationContext(), fileName);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int oneQuarterWidth = (int) (screenWidth * 0.25);

        gridLayout = (GridLayout) findViewById(R.id.root);

        for (int ii = 0; ii < buttonTexts.length; ii++) {
            Button btn = new Button(this);
            btn.setText(buttonTexts[ii]);
            btn.setTextSize(40);

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Button bn = (Button) arg0;
                    String bnText = bn.getText().toString();

                    TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
                    String oldExpression = expressionTextView.getText().toString();
                    char inputCh = bnText.charAt(bnText.length() - 1);
                    if (isOperator(inputCh)) {
                        if (oldExpression.equals(""))
                            return;

                        char lastCh = oldExpression.charAt(oldExpression.length() - 1);
                        if (isOperator(lastCh))
                            return;
                    }

                    if (inputCh == '.' && !appendDotValid(oldExpression)) {
                        return;
                    }

                    String newExpression = null;
                    if (bnText.equals("=")) {
                        double result;

                        replayList.push(oldExpression);
                        saveData();
                        result = getResult(oldExpression);
                        newExpression = Double.toString(result);
                    } else {
                        newExpression = oldExpression.concat(bnText);
                    }

                    expressionTextView.setText(newExpression);
                }
            });

            GridLayout.Spec rowSpec = GridLayout.spec(ii / 4 + 2);
            GridLayout.Spec columnSpec = GridLayout.spec(ii % 4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.width = oneQuarterWidth;
            gridLayout.addView(btn, params);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //showList();
    }

    public void onSin(View v) {
        TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
        String oldExpression = expressionTextView.getText().toString();

        oldExpression = oldExpression.concat("sin(");
        expressionTextView.setText(oldExpression);
    }
    public void onLbracket(View v){
        TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
        String oldExpression = expressionTextView.getText().toString();

        oldExpression = oldExpression.concat("(");
        expressionTextView.setText(oldExpression);
    }

    public void onRbracket(View v){
        TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
        String oldExpression = expressionTextView.getText().toString();

        oldExpression = oldExpression.concat(")");
        Log.i("teest:", oldExpression);
        expressionTextView.setText(oldExpression);
    }

    public void onClearText(View v) {
        TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
        expressionTextView.setText("");
    }

    public void onDeleteText(View v) {
        TextView expressionTextView = (TextView) findViewById(R.id.expressionTextView);
        String oldExp = expressionTextView.getText().toString().trim();
        if (oldExp.equals(""))
            return;

        // Remove the last character.
        oldExp = oldExp.substring(0, oldExp.length() - 1);
        expressionTextView.setText(oldExp);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}