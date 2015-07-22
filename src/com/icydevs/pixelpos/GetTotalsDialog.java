package com.icydevs.pixelpos;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.icydevs.designtemp.R;

public class GetTotalsDialog extends DialogFragment implements
		View.OnClickListener {

	TextView mTextView;
	Button mButton;
	private String data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_get_totals, null);

		mButton = (Button) v.findViewById(R.id.get_totals_button);
		mTextView = (TextView) v.findViewById(R.id.get_totals_text_view);

		mButton.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

	public void setText(String t) {
		this.data = t;
	}
	
	public void showData()
	{
		if(data==null)
		{
			mTextView.setText("There is no data for this date");
		}else{
			mTextView.setText(data);
		}
	}

}
