package com.example.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MyWidget extends AppWidgetProvider {

    class Record{
        public int type;
        public String sell;
        public String buy;
        Record(int t,String s,String b){
            type = t;
            sell = s;
            buy = b;
        }
    }
    Date secondDate;
    String ans="Err\n Err";
    private ArrayList<Record> list = new ArrayList<>();



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Может быть несколько активных виджетов, поэтому обновите их все.
        for (int appWidgetId : appWidgetIds) {
            SendToBase();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
            views.setOnClickPendingIntent(R.id.button,pendingIntent);
            views.setTextViewText(R.id.button,Html.fromHtml(ans));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public void SendToBase()
    {

        new Thread (new Runnable() {
            @Override
            public void run() {
                int k = 0;
                while (list.size() == 0){
                    Calendar cal = Calendar.getInstance();
                    Date currentTime = cal.getTime();
                    cal.add(Calendar.DATE, -k);
                    secondDate = cal.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String date2 = dateFormat.format(currentTime);
                    String date1 = dateFormat.format(secondDate);
                    URL url;
                    String line = "";
                    HttpURLConnection connection = null;

                    k++;
                    try {
                        url = new URL("http://www.cbr.ru/scripts/xml_metall.asp?date_req1=" + date1 + "&date_req2=" + date2);

                        connection = (HttpURLConnection) url.openConnection();
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        line = br.readLine();
                        Log.d("HTTP-GET", line);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
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
                            switch (xpp.getEventType()) {
                                // начало документа
                                case XmlPullParser.START_DOCUMENT:
                                    Log.d(LOG_TAG, "START_DOCUMENT");
                                    break;
                                // начало тэга
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
                                    Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                            + ", depth = " + xpp.getDepth() + ", attrCount = "
                                            + xpp.getAttributeCount());
                                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                                        if (xpp.getAttributeName(i).equals("Code"))
                                            type = Integer.parseInt(xpp.getAttributeValue(i));
                                    }
                                    break;
                                // конец тэга
                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("Record")) {
                                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                                        //newRow(type,buy,sell);
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
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String date3 = dateFormat.format(secondDate);



                ans = "<p>Date: " +date3+"</p>"+"<h2>Золото<h2><p>Покупка: "+list.get(0).buy+"</p><p>Продажа :"+list.get(0).sell+"</p>";

            }
        }).start();
    }

}