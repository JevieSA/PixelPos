package com.icydevs.pixelpos;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.icydevs.designtemp.R;

public class PaymentDialogFragment extends DialogFragment implements OnClickListener {
	
	Button mButtonConfirm, mButtonCancel;
	Spinner mSpinner;
	EditText mEditTextAmount;
	PixelDBAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflating view
		View v = inflater.inflate(R.layout.fragment_payment_method, null);
		
		// Initializing UI elements
		mButtonConfirm = (Button)v.findViewById(R.id.button_payment_confirm);
		mButtonCancel = (Button)v.findViewById(R.id.button_payment_cancel);
		mButtonConfirm.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
		
		mSpinner = (Spinner)v.findViewById(R.id.spinner_payment);
		Resources res = getResources();
		String[] items = res.getStringArray(R.array.payment_list);
		ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
		// a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(a);
		mEditTextAmount = (EditText)v.findViewById(R.id.edit_text_amount);
		
		adapter = new PixelDBAdapter(getActivity());
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.button_payment_confirm:			
			
			double amntOffered = Double.parseDouble(mEditTextAmount.getText().toString());
			double amntDue = ((InvoiceActivity)getActivity()).getTotal();
			if(amntOffered>=amntDue){
				InvoiceActivity act = (InvoiceActivity)getActivity();
				adapter.addInvoiceData(act.getList(), amntDue, mSpinner.getSelectedItem().toString());
				act.clearList();
				Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_LONG).show();
				Toast.makeText(getActivity(), adapter.getAllInvoiceData(), Toast.LENGTH_LONG);
				dismiss();
			}else{
				Toast.makeText(getActivity(), "Amount offered not correct", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.button_payment_cancel:
			dismiss();
			break;
		}
		
	}

}
