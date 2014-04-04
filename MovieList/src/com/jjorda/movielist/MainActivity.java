package com.jjorda.movielist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get references to UI widgets
		ListView myListView = (ListView) findViewById(R.id.listView1);
		final EditText myEditText = (EditText) findViewById(R.id.editText1);
		final Button myButton = (Button) findViewById(R.id.button1);
		// Create the Array List of to do items
		final List<String> todoItems = new ArrayList<String>();
		// Create the Array Adapter to bind the array to the List View
		final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, todoItems);
		// Bind the Array Adapter to the List View
		myListView.setAdapter(aa);
		 myButton.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View arg0) {
		 todoItems.add(0, myEditText.getText().toString());
		 aa.notifyDataSetChanged();
		 myEditText.setText("");
		 }
		
		 });
//		 myListView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction()==MotionEvent.ACTION_MOVE){
//					todoItems.remove(position);
//					aa.notifyDataSetChanged();
//				}
//				return false;
//			}
//		});

		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				todoItems.remove(position);
				aa.notifyDataSetChanged();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getMovie() {

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(
				"https://api.themoviedb.org/3/search/movie?api_key=feb57b96bfa4475b27b8fb6049de49ef&language=es&query=capitan america");
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
