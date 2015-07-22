package com.icydevs.pixelpos;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.icydevs.designtemp.R;

public class RefundDialogFragment extends DialogFragment implements
		View.OnClickListener {

	Button mButtonConfirm, mButtonCancel;
	Spinner mSpinner;
	EditText mEditTextAmount;
	PixelDBAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_payment_method, null);

		// Initializing UI elements
		mButtonConfirm = (Button) v.findViewById(R.id.button_payment_confirm);
		mButtonCancel = (Button) v.findViewById(R.id.button_payment_cancel);
		mButtonConfirm.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);

		mSpinner = (Spinner) v.findViewById(R.id.spinner_payment);
		Resources res = getResources();
		String[] items = res.getStringArray(R.array.payment_list);
		ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, items);
		mSpinner.setAdapter(a);
		mEditTextAmount = (EditText) v.findViewById(R.id.edit_text_amount);

		adapter = new PixelDBAdapter(getActivity());

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_payment_confirm:
			CreditNoteActivity act = (CreditNoteActivity) getActivity();
			double amntDue = 
					act.getTot();
			adapter.addCreditNoteData(
					act.getList(), amntDue,
					mSpinner.getSelectedItem().toString());
			act.clearList();
			dismiss();
			break;
		case R.id.button_payment_cancel:
			dismiss();
			break;
		}

	}

}
