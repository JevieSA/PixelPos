package com.icydevs.pixelpos;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.icydevs.designtemp.R;

public class LoginActivity extends Activity {

	PixelDBAdapter adapter;
	EditText mEditTextUserName, mEditTextPassword;
	CheckBox mCheckBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		adapter = new PixelDBAdapter(this);

		mEditTextUserName = (EditText) findViewById(R.id.edit_text_username);
		mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);

		mCheckBox = (CheckBox) findViewById(R.id.check_box);
	}

	public void login(View v) {
		String name = mEditTextUserName.getText().toString();
		String pass = mEditTextPassword.getText().toString();

		if (adapter.validateUserLogin(name, pass)) {
			SharedPreferences prefs = getSharedPreferences(
					getString(R.string.shared_prefs), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();

			if (mCheckBox.isChecked()) {
				editor.putBoolean("stayLoggedIn", true);
				editor.commit();
			} else {
				editor.putBoolean("stayLoggedIn", false);
				editor.commit();
			}
			Intent main = new Intent(LoginActivity.this, MainActivity.class);
			main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			main.putExtra("restart", true);
			startActivity(main);
		} else {
			Toast.makeText(this, "Login details invalid", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onBackPressed() {
		Intent main = new Intent(LoginActivity.this, MainActivity.class);
		main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		setResult(RESULT_CANCELED);
		finish();
	}

	public void toCreate(View v) {
		CreateUserDialogFragment dialogFragment = new CreateUserDialogFragment();
		FragmentManager manager = getFragmentManager();
		dialogFragment.setStyle(Window.FEATURE_NO_TITLE, 0);
		dialogFragment.show(manager, "CreateUserDialogFragment");
	}
}
