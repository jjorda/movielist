package com.jjorda.movielist.model;

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Movie")
public class Movie extends ParseObject {

	public Movie() {
		super();
	}

	public Movie(String theClassName) {
		super(theClassName);
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		this.put("name", name);
	}

	public ParseFile getImage() {
		return getParseFile("image");
	}

	public void setImage(ParseFile image) {
		this.put("image", image);
	}

	public boolean isWatched() {
		return getBoolean("watched");
	}

	public void setWatched(boolean watched) {
		this.put("watched", watched);
	}

	public MovieList getMovieList() {
		return (MovieList) get("movielist");
	}

	public void setMovieList(MovieList movieList) {
		this.put("movielist", movieList);
	}
	
	public void ssave() {
		try {
			this.save();
		} catch (ParseException e) {
			// TODO test
			e.printStackTrace();
		}
	}
	
	public static List<Movie> getMoviesByList(MovieList list) {
		ParseQuery<Movie> movieQuery = ParseQuery.getQuery(Movie.class).whereEqualTo("movielist", list);
		try {
			return movieQuery.find();
		} catch (ParseException e) {
		//TODO
		}
		return null;
	}
}
