package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    String split;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonListen();
    }

    protected String Calculating() {
        String number = split;
        String[] toAnsw = number.split(" ");
        if(toAnsw.length != 3){
            return "Error";
        }
        else{
            double i = Double.parseDouble(toAnsw[0]);
            double j = Double.parseDouble(toAnsw[2]);
            switch(toAnsw[1]){
                case "+":
                    return Double.toString(i + j);
                case "/":
                    if(j == 0)
                        return "Error Enter C";
                    return Double.toString(i / j);
                case "x":
                    return Double.toString(i * j);
                case "-":
                    return Double.toString(i - j);
            }
        }
        return "Error 1";
    }
    protected void ButtonListen(){
        split = "";
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.Button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);;
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonPoint = findViewById(R.id.buttonPoint);
        Button buttonC = findViewById(R.id.buttonC);
        Button buttonConcl = findViewById(R.id.buttonConcl);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonDel = findViewById(R.id.buttonDel);
        Button buttonEq = findViewById(R.id.buttonEq);
        Button buttonMinus = findViewById(R.id.buttonMinus);

        TextView text = findViewById(R.id.textView);
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "0";
                text.setText(split);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "1";
                text.setText(split);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "2";
                text.setText(split);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "3";
                text.setText(split);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "4";
                text.setText(split);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "5";
                text.setText(split);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "6";
                text.setText(split);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "7";
                text.setText(split);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "8";
                text.setText(split);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += "9";
                text.setText(split);
            }
        });

        buttonPoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += ".";
                text.setText(split);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split = "";
                text.setText(split);
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += " + ";
                text.setText(split);
            }
        });

        buttonConcl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += " x ";
                text.setText(split);
            }
        });
        buttonDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split += " / ";
                text.setText(split);
            }
        });
        buttonEq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                split = Calculating();
                text.setText(split);
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (split.length() < 1){
                    split = "-";
                }
                else {
                    split += " - ";
                }
                text.setText(split);
            }
        });
    }
}