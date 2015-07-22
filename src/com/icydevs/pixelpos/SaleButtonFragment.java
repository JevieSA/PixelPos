package com.icydevs.pixelpos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.icydevs.designtemp.R;

public class SaleButtonFragment extends Fragment implements OnClickListener {
	
	Button mButtonAddItem, mButtonFinalize, mButtonBack;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_sales_button, container, false);
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		mButtonAddItem = (Button)getActivity().findViewById(R.id.sales_button_add_item);
		mButtonBack = (Button)getActivity().findViewById(R.id.sales_button_back);
		mButtonFinalize = (Button)getActivity().findViewById(R.id.sales_button_finalize);
		
		mButtonBack.setOnClickListener(this);
		mButtonAddItem.setOnClickListener(this);
		mButtonFinalize.setOnClickListener(this);
				
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.sales_button_back:
			Intent intent = new Intent(getActivity(), MainActivity.class);
			intent.putExtra("restart", true);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.sales_button_add_item:
			FragmentManager manager = getFragmentManager();
			SearchDialogFragment myDialog = new SearchDialogFragment();
			myDialog.setStyle(Window.FEATURE_NO_TITLE, 0);
			myDialog.show(manager, "SearchDialog");
			break;
		case R.id.sales_button_finalize:
			FragmentManager managerPayment = getFragmentManager();
			PaymentDialogFragment payment = new PaymentDialogFragment();
			payment.setStyle(Window.FEATURE_NO_TITLE, 0);
			payment.show(managerPayment, "PaymentFragment");
			break;
		}
		
	}

}
