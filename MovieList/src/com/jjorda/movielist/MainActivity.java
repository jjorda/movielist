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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String API_KEY = "feb57b96bfa4475b27b8fb6049de49ef";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get references to UI widgets
		ListView myListView = (ListView) findViewById(R.id.listView1);
		// Create the Array List of to do items
		final List<String> todoItems = new ArrayList<String>();
		// Create the Array Adapter to bind the array to the List View
		final ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		// Bind the Array Adapter to the List View
		myListView.setAdapter(aa);

		// Edit text
		final EditText newFilmText = (EditText) findViewById(R.id.editText1);
		newFilmText.setImeActionLabel("Add", EditorInfo.IME_ACTION_SEND);
		newFilmText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					// TODO *1 repeated code
					todoItems.add(0, newFilmText.getText().toString());
					aa.notifyDataSetChanged();
					newFilmText.setText("");
				}
				return true;
			}
		});
		// Button
		final Button myButton = (Button) findViewById(R.id.button1);
		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO *1 repeated code
				todoItems.add(0, newFilmText.getText().toString());
				aa.notifyDataSetChanged();
				newFilmText.setText("");
			}

		});

		myListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				new AlertDialog.Builder(MainActivity.this).setTitle(R.string.remove).setMessage(R.string.film_remove_dialog)
						.setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								todoItems.remove(position);
								aa.notifyDataSetChanged();
							}
						}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							}
						}).show();

				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getMovie(String language, String filmName) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		HttpGet httpGet = new HttpGet("https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=" + language + "&query=" + filmName);
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
