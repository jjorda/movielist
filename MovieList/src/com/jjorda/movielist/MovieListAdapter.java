package com.jjorda.movielist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jjorda.movielist.model.Movie;
import com.jjorda.movielist.model.MovieList;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MovieListAdapter extends BaseAdapter {

	private List<Movie> movieItems;
	private LayoutInflater mInflater;
	private MovieList currentList;

	public MovieListAdapter(List<Movie> movies, Context context) {
		super();
		// start new code
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {

			List<MovieList> movieLists = MovieList.findMovieListsByUser(currentUser);

			if (movieLists.size() == 0) {
				MovieList movieList = new MovieList();
				movieList.setListName("list1");

				movieList.addUser(currentUser);
				movieList.ssave();
				currentList = movieList;

				// TODO check
				installList(movieList, currentUser);

			} else {

				currentList = movieLists.get(0);
				movies = Movie.getMoviesByList(currentList);
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

		Movie movie = new Movie();
		movie.setName(movieName);
		movie.setMovieList(currentList);
		movie.ssave();

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
	private void installList(MovieList movieList, ParseUser user) {

		ParseInstallation pInstal = ParseInstallation.getCurrentInstallation();
		List<MovieList> movielists = (List<MovieList>) pInstal.get("movielists");
		movielists.add(movieList);
		pInstal.put("movielists", movielists);
		pInstal.put("user", user);
		try {
			pInstal.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
