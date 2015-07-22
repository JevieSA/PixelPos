package com.icydevs.pixelpos;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;

import com.icydevs.designtemp.R;
import com.icydevs.pixelpos.PixelDBAdapter.PixelPosDB;

public class MainActivity extends ActionBarActivity {

	boolean restart = false;
	PixelDBAdapter adapter;
	static final int LOGIN_REQUEST_CODE = 0;
	String disp = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = getSharedPreferences(
				getString(R.string.shared_prefs), Context.MODE_PRIVATE);
		boolean stayLoggedIn = prefs.getBoolean("stayLoggedIn", false);

		if (stayLoggedIn) {
			setContentView(R.layout.activity_main);
		} else {
			Intent firstStart = getIntent();

			if (firstStart != null) {
				restart = firstStart.getBooleanExtra("restart", false);
			}
			// Checking if the activity is being started for the first time
			if (!restart) {
				// Logging the user into the application
				Intent loginIntent = new Intent(MainActivity.this,
						LoginActivity.class);
				loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(loginIntent, LOGIN_REQUEST_CODE);
			} else {
				setContentView(R.layout.activity_main);
			}
			adapter = new PixelDBAdapter(this);
		}
	}

	public void onClick(View v) {

		Intent intent;

		switch (v.getId()) {
		case R.id.button_sales:
			intent = new Intent(MainActivity.this, InvoiceActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.button_search:
			FragmentManager ft = getFragmentManager();
			SearchDialogFragment searchFrag = new SearchDialogFragment();
			searchFrag.show(ft, "DialogFragment");
			break;
		case R.id.button_credit_note:
			intent = new Intent(MainActivity.this, CreditNoteActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.button_add_stock:
			intent = new Intent(MainActivity.this, AddItemActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.button_log_out:
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("restart", false);
			SharedPreferences prefs = getSharedPreferences(
					getString(R.string.shared_prefs), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("stayLoggedIn", false);
			editor.commit();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.button_get_totals:
			disp = "";
			DateSelectFragment dialog = new DateSelectFragment();
			OnDateSetListener onDate = new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int month,
						int day) {
					GetTotalsDialog dispDialog = new GetTotalsDialog();
					PixelDBAdapter dispAdapter = new PixelDBAdapter(
							getApplicationContext());
					disp = dispAdapter.getDayEndTotals(year, month + 1, day);					
//					dispDialog.setText(disp);
//					dispDialog.showData();
//					dispDialog.show(getFragmentManager(), "GetTotalsDialog");
				}
			};
			dialog.setStyle(Window.FEATURE_NO_TITLE, 0);
			dialog.show(getFragmentManager(), "DateSelectFragment");
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			this.finish();
		}
	}
}
