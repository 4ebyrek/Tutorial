package com.example.batman.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends Activity {
	public Elements content;

	public ArrayList<String> titleList = new ArrayList<String>();
	public ArrayList<String> urls = new ArrayList<String>();
	public ArrayList<String>  newstext =new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ListView lv;
	int cnt=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("############","itemclick:position= "+position+" id="+id);
				Intent intent = new Intent(MainActivity.this,Main2Activity.class);
				//intent.putExtra("Title",titleList);
				startActivity(intent);
			}
		});

		new NewThread().execute();

		adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.pro_item,titleList);

	}

	public void logUrls(){
		for(int i=0;i<urls.size();i++){
			Log.d("*********", "Element url = "+i+" "+urls.get(i));
		}
	}
	public void logNews() {
		for (int i = 0; i < newstext.size(); i++) {
			Log.d("*********", "News_content = " + i + " " + newstext.get(i));
		}
	}
	public void logtitle() {
		for (int i = 0; i < titleList.size(); i++) {
			Log.d("*********", "News_content = " + i + " " + titleList.get(i));
		}
	}

public class NewThread extends AsyncTask<String,Void,String>{


	@Override
	protected String doInBackground(String... params) {

		//Document doc1;
		try {
//			doc1 = Jsoup.connect("http://www.sports.ru/topnews/football/").get();
//			Elements link =doc1.select(".short-text");
//			titleList.clear();
//			for( Element contens:link){
//
//				//titleList.add(contens.text());
//				titleList.add("http://www.sports.ru"+href);
//
//			}
			Document doc;
			doc = Jsoup.connect("http://www.sports.ru/topnews/").get();
			content= doc.select(".short-text");
			titleList.clear();
			newstext.clear();
			for( Element contens:content){
				String href = "http://sports.ru"+contens.attr("href");
				Document docForNewsText = Jsoup.connect(href).get();
				Elements contentForNewsText = docForNewsText.select(".news-item__content");
				newstext.add(contentForNewsText.text());
				titleList.add(contens.text());
			}
		}catch (IOException e) {
			e.printStackTrace();

		}
		return null;

	}

	@Override
	protected void onPostExecute(String result){
		lv.setAdapter(adapter);
		logUrls();
		logNews();
		logtitle();
//		for(int i=0;i<urls.size();i++){
//			Log.d("*********", "Element nomer = "+i+" " + urls.get(i));
//		}

//		Log.d("*********", "Element nomer = " + urls.size());
//		Log.d("*********", "Element nomer = " + titleList.size());
	}

}


}
