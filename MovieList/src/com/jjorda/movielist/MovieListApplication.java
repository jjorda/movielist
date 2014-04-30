package com.jjorda.movielist;

import android.app.Application;

import com.jjorda.movielist.model.Movie;
import com.jjorda.movielist.model.MovieList;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;

public final class MovieListApplication extends Application {
	private static final String APPLICATION_ID = "EVEg7obITwbN9d3b8ogq2EOM6ys51wxdZUEAWdPV";
	private static final String CLIENT_KEY = "FkSA9jBCCkKrLpVj9mcLzkSYevT7kXWM2Uy5oPOe";

	@Override
	public void onCreate() {
		super.onCreate();
		//Parse Mapping
		ParseObject.registerSubclass(Movie.class);
		ParseObject.registerSubclass(MovieList.class);

		// Initialize the Parse SDK.
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);

		// TODO: test push notifications need to swap this, this channel now
		// is generic but we need this channel is used only by 1 list
		//PushService.subscribe(this, "MoviePush", MainActivity.class);

	}

}
