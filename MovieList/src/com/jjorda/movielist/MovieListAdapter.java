package com.jjorda.movielist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MovieListAdapter extends BaseAdapter {
	// da list
	private List<String> movieItems;
	private LayoutInflater mInflater;

	public MovieListAdapter(List<String> movieItems, Context context) {
		super();
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
		//0 because we want add ever in the first place
		movieItems.add(0,movieName);
		this.notifyDataSetChanged();
	}

	public void remove(int position) {
		movieItems.remove(position);
		this.notifyDataSetChanged();
	}

}
