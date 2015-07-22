package com.icydevs.pixelpos;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icydevs.designtemp.R;

public class CreateItemDialog extends DialogFragment implements
		View.OnClickListener {

	PixelDBAdapter adapter;
	Button mButtonConfirm, mButtonCancel;
	EditText mItemId, mDescription, mPricBuy, mPriceSell, mCategory,
			mSupplier;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_add_item, null);

		mItemId = (EditText) v.findViewById(R.id.editText_id);
		mDescription = (EditText) v.findViewById(R.id.editText_description);
		mPricBuy = (EditText) v.findViewById(R.id.editText_price_buy);
		mPriceSell = (EditText) v.findViewById(R.id.editText_price_sell);
		mCategory = (EditText) v.findViewById(R.id.editText_category);
		mSupplier = (EditText) v.findViewById(R.id.editText_supplier);

		mButtonConfirm = (Button) v.findViewById(R.id.button_confirm);
		mButtonCancel = (Button) v.findViewById(R.id.button_cancel);

		mButtonConfirm.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);

		adapter = new PixelDBAdapter(getActivity());

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_confirm:
			addItem();
			Toast.makeText(getActivity(), adapter.getAllItemData(),
					Toast.LENGTH_LONG).show();
			dismiss();
			break;
		case R.id.button_cancel:
			dismiss();
			break;
		}
	}

	public void addItem() {

		try {
			String id = mItemId.getText().toString();
			String desc = mDescription.getText().toString();
			double priceBuy = Double.parseDouble(mPricBuy.getText().toString());
			double priceSell = Double.parseDouble(mPriceSell.getText()
					.toString());
			String category = mCategory.getText().toString();
			String supplier = mSupplier.getText().toString();

			int quant = 0;
			
			adapter.insertItem(id, desc, priceBuy, priceSell, quant, category,
					supplier);
			Log.d("-- DB -- ", "Successfully placed data into db");
			Toast.makeText(getActivity(), "New item created.",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.d("-- DB --", "Insert Failed");
			Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

}
