package com.icydevs.pixelpos;

import java.util.Calendar;

import com.icydevs.designtemp.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;

public class DateSelectFragment extends DialogFragment {

	PixelDBAdapter adapter;
	OnDateSetListener dateSet;
	String display;
	
	Calendar now = Calendar.getInstance();
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		setStyle(Window.FEATURE_NO_TITLE, R.style.AppTheme);
		return new DatePickerDialog(getActivity(), dateSet, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		
	}

	public void setCallBack(OnDateSetListener onDate)
	{
		dateSet = onDate;
	}
	
	public String returnDisplay()
	{
		return display;
	}
}
