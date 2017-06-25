package com.example.zhou.networkcontactstest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String icon = "";

    public static final int UPDATE_TEXT = 1;

    private List<Contacts> contactsList = new ArrayList<>();

    private Handler handler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    ContactsAdapter adapter = new ContactsAdapter(MainActivity.this, R.layout.contacts_item, contactsList);
                    ListView listView = (ListView)findViewById(R.id.list_view);
                    listView.setAdapter(adapter);

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button readContacts = (Button)findViewById(R.id.read_contacts);
        readContacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_contacts){
            sendRequestWithOkHttp();
        }
    }
     private void   sendRequestWithOkHttp(){
        new Thread(new Runnable() {
          @Override
           public void run() {
              try {
                  OkHttpClient client = new OkHttpClient();
                  Request request = new Request.Builder()
                          .url("https://raw.githubusercontent.com/jxr202/XmlTest/master/contacts.xml")
                          .build();
                  Response response = client.newCall(request).execute();
                  String responseData = response.body().string();
                  parseXMLWithPull(responseData);
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
      }).start();
  }

    public void parseXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id ="";
            String name = "";
            String telephone = "";

            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if ("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if ("telephone".equals(nodeName)){
                            telephone = xmlPullParser.nextText();
                        }else if ("icon".equals(nodeName)){
                            icon = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if ("contact".equals(nodeName)){
                            Log.d("MainActivity", "id is  "+ id);
                            Log.d("MainActivity", "name is " + name);
                            Log.d("MainActivity", "telephone is "+ telephone);
                            Log.d("MainActivity", "icon is " + icon);
                            Contacts data = new Contacts(name, telephone, getBitmap(icon));
                            contactsList.add(data);
                            Message message = new Message();
                            message.what = UPDATE_TEXT;
                            handler.sendMessage(message);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private Bitmap getBitmap(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();
        return bitmap;
    }
}
