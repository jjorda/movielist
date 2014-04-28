package com.jjorda.movielist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MovieListAdapter extends BaseAdapter {

	private List<ParseObject> movieItems;
	private LayoutInflater mInflater;
	private ParseObject currentList;

	public MovieListAdapter(List<ParseObject> movies, Context context) {
		super();
		// start new code
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {

			try {
				ParseQuery<ParseObject> listQuery = ParseQuery.getQuery("MovieList").whereEqualTo("users", currentUser);
				List<ParseObject> movieLists = listQuery.find();
				if (movieLists.size() == 0) {
					ParseObject movieList = new ParseObject("MovieList");
					movieList.put("listname", "list1");

					// TODO check that
					List<ParseObject> users = new ArrayList<ParseObject>();
					users.add(currentUser);

					movieList.put("users", users);
					movieList.save();
					currentList = movieList;

					// TODO check
					installList(movieList, currentUser);

				} else {

					currentList = movieLists.get(0);
					ParseQuery<ParseObject> movieQuery = ParseQuery.getQuery("Movie").whereEqualTo("movielist", currentList);
					movies = movieQuery.find();
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// TODO ¿que pasa si no estás logado?
		}
		this.movieItems = movies;
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
		ParseObject movie = (ParseObject) getItem(position);
		textView.setText(movie.getString("name"));
		return convertView;
	}

	public void add(String movieName) {
		// parse block

		ParseObject movie = new ParseObject("Movie");
		movie.put("name", movieName);

		movie.put("movielist", currentList);
		try {
			movie.save();
		} catch (ParseException e) {
			// TODO something
			e.printStackTrace();
		}

		// list block
		movieItems.add(0, movie);
		this.notifyDataSetChanged();

	}

	public void remove(int position) {
		// parse block
		ParseObject movie = movieItems.get(position);
		try {
			// TODO Bad reference, we can use name for references the object.
			movie.delete();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// list block
		movieItems.remove(position);
		this.notifyDataSetChanged();
	}

	// TODO check
	private void installList(ParseObject movieList, ParseUser user) throws ParseException {
		ParseInstallation pInstal = ParseInstallation.getCurrentInstallation();
		List<ParseObject> movielists = (List<ParseObject>) pInstal.get("movielists");
		movielists.add(movieList);
		pInstal.put("movielists", movielists);
		pInstal.put("user", user);
		pInstal.save();
	}

}
