package com.icydevs.pixelpos;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.icydevs.designtemp.R;

public class ProductItemAdapter extends ArrayAdapter<ProductItem> {

	private ArrayList<ProductItem> m_items;
	TextView mTextViewId, mTextViewDescription, mTextViewQuantity,
			mTextViewPrice;

	public ProductItemAdapter(Context context, int textViewResourceId,
			ArrayList<ProductItem> objects) {
		super(context, textViewResourceId, objects);
		this.m_items = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		// Checking whether there is a view to inflate
		// Inflates TextViews
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.search_list_item, null);

			mTextViewId = (TextView) v.findViewById(R.id.text_view_list_id);
			mTextViewQuantity = (TextView) v
					.findViewById(R.id.text_view_list_quant);
			mTextViewPrice = (TextView) v
					.findViewById(R.id.text_view_list_price);
			mTextViewDescription = (TextView) v
					.findViewById(R.id.text_view_list_name);

		}

		ProductItem item = m_items.get(position);

		// Setting list view when items are searched
		try {
			if (item != null) {
				if (mTextViewId.getId() == R.id.text_view_list_id) {
					String dispId = item.getId() + "";
					mTextViewId.setText(dispId);
				}
				if (mTextViewQuantity.getId() == R.id.text_view_list_quant) {
					String dispQ = item.getQuantity() + "";
					mTextViewQuantity.setText(dispQ);
				}
				if (mTextViewPrice.getId() == R.id.text_view_list_price) {
					String dispP = item.getPriceSell() + "";
					mTextViewPrice.setText(dispP);
				}
				if (mTextViewDescription.getId() == R.id.text_view_list_name) {
					String dispD = item.getDescription() + "";
					mTextViewDescription.setText(dispD);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}
}
