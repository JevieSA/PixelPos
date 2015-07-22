package com.icydevs.pixelpos;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icydevs.designtemp.R;

public class CreateUserDialogFragment extends DialogFragment implements
		View.OnClickListener {

	PixelDBAdapter adapter;
	EditText mUsername, mPassword, mPassConfirm;
	Button mButtonCreate, mButtonBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_login_create, null);

		mUsername = (EditText) v.findViewById(R.id.edit_text_username);
		mPassword = (EditText) v.findViewById(R.id.edit_text_password);
		mPassConfirm = (EditText) v
				.findViewById(R.id.edit_text_password_confirm);

		mButtonCreate = (Button) v.findViewById(R.id.button_create);
		mButtonBack = (Button) v.findViewById(R.id.button_login_back);

		mButtonCreate.setOnClickListener(this);
		mButtonBack.setOnClickListener(this);

		adapter = new PixelDBAdapter(getActivity());

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.button_create:
			String name = mUsername.getText().toString();
			String pass = mPassword.getText().toString();
			String passConf = mPassConfirm.getText().toString();

			if (!adapter.testDuplicate(name)) {
				if (pass.equals(passConf)) {
					if (!name.equals("")||!name.equals(null)) {
						if (8 <= pass.length()) {
							boolean num = false;
							boolean ch = false;

							for (int i = 0; i < pass.length(); i++) {
								if (checkLetter(pass)) {
									ch = true;
								}
								if (checkNum(pass)) {
									num = true;
								}
							}

							if (ch && num) {

								try {
									adapter.insert(name, pass);
									Toast.makeText(getActivity(),
											"New user created.",
											Toast.LENGTH_SHORT).show();
								} catch (Exception e) {
									Toast.makeText(getActivity(),
											"Insert Failed", Toast.LENGTH_SHORT)
											.show();
									e.printStackTrace();
								}

							} else {
								Toast.makeText(
										getActivity(),
										"Password needs to contain numbers and letters",
										Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(
									getActivity(),
									"Password needs to be atleast 8 characters long",
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(getActivity(), "Passwords do not match",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getActivity(), "Username cannot be blank",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getActivity(), "Username already taken",
						Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_login_back:
			dismiss();
			break;

		}

	}

	public boolean checkLetter(String pass) {
		boolean check = false;

		char[] charArr = pass.toCharArray();

		for (char c : charArr) {
			if (Character.isLetter(c)) {
				check = true;
			}
		}

		return check;
	}

	public boolean checkNum(String pass) {
		boolean check = false;

		char[] charArr = pass.toCharArray();

		for (char c : charArr) {
			if (Character.isDigit(c)) {
				check = true;
			}
		}

		return check;
	}
}
