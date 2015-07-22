package com.icydevs.pixelpos;

import java.util.ArrayList;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.icydevs.designtemp.R;

public class SearchDialogFragment extends DialogFragment implements
		OnItemClickListener {

	PixelDBAdapter adapter;
	EditText mEditTextSearchInput;
	ArrayList<ProductItem> list;
	ProductItemAdapter m_adapter;
	ListView myView;
	Button searchClick;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflating the Search Dialog View
		View v = inflater.inflate(R.layout.activity_search, null);

		// Assigning UI elements to layout IDs
		myView = (ListView) v.findViewById(android.R.id.list);
		mEditTextSearchInput = (EditText) v
				.findViewById(R.id.edit_texdt_search_input);

		// Initializing Variables
		adapter = new PixelDBAdapter(getActivity());

		// Setting onClickListener for search button
		searchClick = (Button) v.findViewById(R.id.button_search);
		searchClick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strSearch = mEditTextSearchInput.getText().toString();

				// Querying the DB for all elements that contain the search
				// String
				list = adapter.getItemData(strSearch);

				// Initializing the Product Item Adapter
				m_adapter = new ProductItemAdapter(v.getContext(),
						R.layout.search_list_item, list);

				// Setting the list view to display DB query results
				myView.setAdapter(m_adapter);
			}
		});

		// Setting onItemClickListener for the list view
		myView.setOnItemClickListener(this);
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		final int pos = position;

		try {
			list.get(pos).setQuantity(1);

			// Calling showList() method to append the ProductItem object that
			// was
			// selected from the ListView to the ArrList<ProductItem> in
			// InvoiceActivity
			((InvoiceActivity) getActivity()).addListItem(list.get(pos));

			// Dismiss the DialogFragment to allow the user to interact with
			// InvoiceActivity
			this.dismiss();
		} catch (Exception e) {
			try {
				list.get(pos).setQuantity(1);

				// Calling showList() method to append the ProductItem object
				// that was selected
				// from the ListView to the ArrList<ProductItem> in
				// CreditNoteActivity
				((CreditNoteActivity) getActivity()).showList(list.get(pos));

				// Dismiss the DialogFragment to allow the user to interact with
				// CreditNoteActivity
				this.dismiss();
				list.get(pos).setQuantity(1);
			} catch (Exception e1) {
				try {
					// Calling showList() method to append the ProductItem
					// object
					// that
					// was
					// selected from the ListView to the ArrList<ProductItem> in
					// AddItemActivity
					((AddItemActivity) getActivity()).showList(list.get(pos));

					// Dismiss the DialogFragment to allow the user to interact
					// with
					// AddItemActivity
					this.dismiss();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
	}
}
