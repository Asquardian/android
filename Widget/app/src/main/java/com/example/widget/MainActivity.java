package com.example.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.XMLFormatter;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    // таблица
    private TableLayout table;
    //параметры в таблице
    private TableRow.LayoutParams param;
    //класс записи
    class Record{
        public int type;//тип металла
        public String sell;// покупка
        public String buy;//продажа
        Record(int t,String s,String b){
            type = t;
            sell = s;
            buy = b;
        }
    }
    Date secondDate;
    private ArrayList<Record> list = new ArrayList<>();//список

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// делаем ссылку на таблицу
        table = findViewById(R.id.table);
        //расположение таблицы
        param = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
        );
//бращаемся к виджету
        SendToBase();
    }
//вид таблицы
    public  void newRow(Record r){
        Log.d("tet","!");
        TextView name = new TextView(MainActivity.this);
        TextView buy = new TextView(MainActivity.this);
        TextView sell = new TextView(MainActivity.this);
        name.setLayoutParams(param);
        name.setPadding(15, 15, 15, 15);
        name.setGravity(Gravity.CENTER);
        buy.setLayoutParams(param);
        buy.setPadding(15, 15, 15, 15);
        buy.setGravity(Gravity.CENTER);
        sell.setPadding(15, 15, 15, 15);
        sell.setLayoutParams(param);
        sell.setGravity(Gravity.CENTER);
        switch (r.type){
            case 1:name.setText("Золото");break;
            case 2:name.setText("Серебро");break;
            case 3:name.setText("Платина");break;
            case 4:name.setText("Палладий");break;

        }
        buy.setText(r.buy);
        sell.setText(r.sell);
        TableRow row = new TableRow(MainActivity.this);
        row.addView(name);
        row.addView(buy);
        row.addView(sell);
        table.addView(row);


    }


    public void SendToBase()
    {

        new Thread (new Runnable() {
            @Override
            public void run() {
                int k = 0;
                while (list.size() == 0){
                    Calendar cal = Calendar.getInstance();//getInstance возвращает экземпляр класса
                    Date currentTime = cal.getTime();//getTime возвращает числовое значение указанной дате
                    cal.add(Calendar.DATE, -k);//преобразуем наш объект к типу Date
                    secondDate = cal.getTime();
                    //формат даты
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String date2 = dateFormat.format(currentTime);
                    String date1 = dateFormat.format(secondDate);
                    URL url;
                    String line = "";
                    HttpURLConnection connection = null;

                    k++;
                    try {
                        //ссылка с которой мы берём ресурсы
                        url = new URL("http://www.cbr.ru/scripts/xml_metall.asp?date_req1=" + date1 + "&date_req2=" + date2);
                        //подключаемся к ссылке
                        connection = (HttpURLConnection) url.openConnection();
                         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        //чтение построчно
                        line = br.readLine();
                        Log.d("HTTP-GET", line);

                    } catch (IOException e) {
                        e.printStackTrace();//вывод метода который в данный момент выполняется
                    } finally {
                        if (connection != null) {
                            connection.disconnect();//если не подключились к ссылке
                        }
                    }
                    //работа с тэгами
                    final String LOG_TAG = "myLogs";
                    try {
                        XmlPullParserFactory factory = XmlPullParserFactory
                                .newInstance();

                        factory.setNamespaceAware(true);
                        XmlPullParser xpp = factory.newPullParser();
                        xpp.setInput(new StringReader(line));
                        int type = 0;
                        String sell = "";
                        String buy = "";
                        int state = 0;
                        while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                            switch (xpp.getEventType()) {//Позиция внутри документа представлена в виде текущего события
                                // начало документа
                                case XmlPullParser.START_DOCUMENT:
                                    Log.d(LOG_TAG, "START_DOCUMENT");
                                    break;
                                // начало тэга
                                //getName можно извлечь имя любого тега
                                case XmlPullParser.START_TAG:
                                    if (xpp.getName().equals("Record")) {
                                        state = 1;
                                    } else if (xpp.getName().equals("Buy")) {
                                        state = 2;
                                        break;
                                    } else if (xpp.getName().equals("Sell")) {
                                        state = 3;
                                        break;
                                    }
                                    //getDepht-определяет глубину рекурсии
                                    Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                            + ", depth = " + xpp.getDepth() + ", attrCount = "
                                            + xpp.getAttributeCount());
                                    //считаем кол-во атрибутов заданного элемента
                                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                                        if (xpp.getAttributeName(i).equals("Code"))
                                            type = Integer.parseInt(xpp.getAttributeValue(i));
                                    }
                                    break;
                                // конец тэга
                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("Record")) {
                                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                                        Record temp = new Record(type, buy, sell);
                                        if (temp != null)
                                            list.add(temp);
                                        type = 0;
                                        buy = "";
                                        sell = "";
                                    }
                                    break;
                                // содержимое тэга
                                case XmlPullParser.TEXT:
                                    if (state == 2) buy = xpp.getText();
                                    else if (state == 3) sell = xpp.getText();
                                    Log.d(LOG_TAG, "text = " + xpp.getText());
                                    break;

                                default:
                                    break;
                            }
                            // следующий элемент
                            xpp.next();
                        }
                        Log.d(LOG_TAG, "END_DOCUMENT");

                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for(Record el:list){
                                newRow(el);
                            }
                            TextView text = findViewById(R.id.date);
                            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                            String date3 = dateFormat.format(secondDate);
                            text.setText("Date: "+ date3);
                        }
                    });
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}