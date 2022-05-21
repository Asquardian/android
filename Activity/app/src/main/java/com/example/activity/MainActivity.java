package com.example.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  ListView listView = findViewById(R.id.listview);
        String[] catNames = getResources().getStringArray(R.array.cat_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);
        */




        BottomNavigationView bottomNav= findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new first()).commit();
        getSupportActionBar().setTitle("lab2");

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String header = "";
                switch (item.getItemId()){
                    case R.id.first:
                        fragment = new first();
                        header = "1";
                        break;
                    case R.id.second:
                        fragment = new second();
                        header = "2";
                        break;
                    case R.id.third:
                        fragment = new third();
                        header = "3";
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                //getSupportActionBar().setTitle(header);
                return false;
            }
        });


    }


}