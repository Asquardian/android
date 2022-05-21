package com.example.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database db = new Database(this);
        TextView textView = new TextView(this);
        textView.setText(db.open());
        ConstraintLayout l = findViewById(R.id.layout1);
        l.addView(textView);
    }
}