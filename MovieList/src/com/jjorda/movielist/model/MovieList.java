package com.jjorda.movielist.model;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("MovieList")
public class MovieList extends ParseObject {

	public MovieList() {
		super();
	}

	public MovieList(String theClassName) {
		super(theClassName);
	}

	public String getListName() {
		return getString("listname");
	}

	public void setListName(String listName) {
		this.put("listname", listName);
	}

	public List<ParseUser> getUsers() {
		return getList("users");
	}

	public void setUsers(List<ParseUser> users) {
		this.put("users", users);
	}

	public void addUser(ParseUser user) {
		if (getUsers() != null) { // TODO test that
			getUsers().add(user);
		} else {
			List<ParseUser> users = new ArrayList<ParseUser>();
			users.add(user);
			setUsers(users);
		}
	}

	public void ssave() {
		try {
			this.save();
		} catch (ParseException e) {
			// TODO test
			e.printStackTrace();
		}
	}

	public static List<MovieList> findMovieListsByUser(ParseUser currentUser) {
		ParseQuery<MovieList> listQuery = ParseQuery.getQuery(MovieList.class).whereEqualTo("users", currentUser);
		try {
			return listQuery.find();
		} catch (ParseException e) {
			// TODO no internet?
		}
		return null;
	}

}