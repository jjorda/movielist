package com.jjorda.movielist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MovieListAdapter extends BaseAdapter {
	
	private String APPLICATION_ID="EVEg7obITwbN9d3b8ogq2EOM6ys51wxdZUEAWdPV";
	private String CLIENT_KEY="FkSA9jBCCkKrLpVj9mcLzkSYevT7kXWM2Uy5oPOe";

	// da list
	private List<String> movieItems;
	private LayoutInflater mInflater;
	
	

	public MovieListAdapter(List<String> movieItems, Context context) {
		super();
		
		Parse.initialize(context, APPLICATION_ID, CLIENT_KEY);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
		try {
			List<ParseObject> movies =query.find();
			for(ParseObject movie : movies){
				movieItems.add(movie.getString("name"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		this.movieItems = movieItems;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int position) {
		return movieItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.movie_list_item, null);

		TextView textView = (TextView) convertView.findViewById(R.id.itemTextView);
		textView.setText((String) getItem(position));
		return convertView;
	}

	public void add(String movieName) {
		// parse block
		ParseObject movie = new ParseObject("Movie");
		movie.put("name", movieName);
		try {
			movie.save();
		} catch (ParseException e) {
			//TODO something
			e.printStackTrace();
		}

		// list block
		movieItems.add(0, movieName);
		this.notifyDataSetChanged();

	}

	public void remove(int position) {
		// parse block
		String movieName = movieItems.get(position);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
		query.whereEqualTo("name", movieName);
		try {
			//TODO Bad reference, we can use name for references the object.
			ParseObject movie = query.getFirst();
			movie.delete();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// list block
		movieItems.remove(position);
		this.notifyDataSetChanged();
	}

}
