package com.example.tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {


    public void onTabChanged(String tabId) {
        Log.d("TAB_CHANGE","onTabChanged(): tabId=" + tabId);
        updateTab(tabId, R.id.tab_1,new YourFragment());
    }

    public void updateTab(String tabId, int placeholder,Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(placeholder,fragment, tabId)
                .commit();
    }
    private static View createTabView(final Context context, final String text) {
        int resouceId = R.layout.tabs_bg;
        if(text.equals("Tab1")) {
            resouceId = R.layout.tab_1_layout;
        } else if(text.equals("Tab2")) {
            resouceId = R.layout.tab_2_layout;
        }
        View view = LayoutInflater.from(context).inflate(resouceId, null);
        return view;
    }
    private void setupTabs() {
        mTabHost.setup();
        setupTab(new TextView(this), "Tab1");
        setupTab(new TextView(this), "Tab2");
        setupTab(new TextView(this), "Tab3");
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.empty);//not necessery

    }
    @Override
    public void onTabChanged(String s) {
        tabHost.setup();
        setupTab(new TextView(this), "Tab1");
        setupTab(new TextView(this), "Tab2");
        setupTab(new TextView(this), "Tab3");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.Tab2Content);
        String[] Names = new String[] {
                "Джозеф", "Джотаро",
                "Спидвагон", "Джонатан", "Джорно"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Names);

        listView.setAdapter(adapter);

        TableLayout containerTableLayout = findViewById(R.id.Tab3Content);

        TableRow tableRow1 = new TableRow(this);
        TableRow tableRow2 = new TableRow(this);
        EditText editTextLogin = new EditText(this);
        editTextLogin.setHint("Введите");
        EditText editTextLogin2 = new EditText(this);
        editTextLogin2.setHint("Введите");
        tableRow1.addView(editTextLogin);
        tableRow2.addView(editTextLogin2);
        containerTableLayout.addView(tableRow1);
        containerTableLayout.addView(tableRow2);


        setTitle("TabHost");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setOnTabChangedListener(this);

        setupTabs();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.Tab1);
        tabSpec.setIndicator("Speed");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.Tab2Content);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.Tab3Content);
        tabSpec.setIndicator("IDK");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }



}