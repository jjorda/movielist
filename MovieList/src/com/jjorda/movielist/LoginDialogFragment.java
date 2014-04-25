package com.jjorda.movielist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final View view = (View) LayoutInflater.from(getActivity()).inflate(R.layout.login, null);
		final EditText userEditText = (EditText) view.findViewById(R.id.usernameEditText);
		final EditText passEditText = (EditText) view.findViewById(R.id.passwordEditText);

		final TextView loginTextView = (TextView) view.findViewById(R.id.loginTextView);
		final TextView signinTextView = (TextView) view.findViewById(R.id.signinTextView);

		loginTextView.setTextColor(Color.BLUE);

		loginTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginTextView.setTextColor(Color.BLUE);
				signinTextView.setTextColor(Color.BLACK);
			}
		});

		signinTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginTextView.setTextColor(Color.BLACK);
				signinTextView.setTextColor(Color.BLUE);
			}
		});

		builder.setView(view)

		.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// check
//				if (loginTextView.getTextColors().equals(Color.BLUE)) {
					ParseUser.logInInBackground(userEditText.getText().toString(), passEditText.getText().toString(), new LogInCallback() {
						public void done(ParseUser user, ParseException e) {
							if (user != null) {
								getActivity().runOnUiThread(new Runnable() {
							        @Override
							        public void run() {
										Toast.makeText(getActivity(), "mierda", Toast.LENGTH_LONG).show();
							        }
							    });
								
								Log.d("DEBUG", "logged in: " + user.toString());
							} else {
								Log.d("DEBUG", "fail logging");
							}
						}
					});
//				} else {
//					Log.d("DEBUG", "signing");
//				}
			}
		}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				LoginDialogFragment.this.getDialog().cancel();
			}
		});
		return builder.create();
	}
}
