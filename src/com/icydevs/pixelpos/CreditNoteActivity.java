package com.icydevs.pixelpos;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.icydevs.designtemp.R;

public class CreditNoteActivity extends ListActivity {

	ProductItemAdapter itemAdapter;
	ListView mListView;
	ArrayList<ProductItem> list;
	double tot;
	TextView mTextViewTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_note);

		mListView = (ListView) findViewById(android.R.id.list);
		mTextViewTotal = (TextView) findViewById(R.id.text_view_total_2);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				CreditEditItemDialogFragment dialog = new CreditEditItemDialogFragment();
				dialog.setStyle(Window.FEATURE_NO_TITLE, 0);
				dialog.show(getFragmentManager(), "EditItemDialogFragment");
			}
		});

		showButtonFragment();
		list = new ArrayList<ProductItem>();
	}

	public void getTotal() {
		tot = 0;
		for (ProductItem temp : list) {
			tot = tot + (temp.getQuantity() * temp.getPriceSell());
		}
	}

	public void showList(ProductItem listTemp) {
		if (listTemp != null) {
			list.add(listTemp);
			getTotal();
			mTextViewTotal.setText("R " + tot);
		}
		try {
			itemAdapter = new ProductItemAdapter(this, R.id.list_item, list);
			setListAdapter(itemAdapter);

		} catch (Exception e) {
			Log.d("-- ListView --",
					"Could not display ListView in CreditNoteActivity");
			e.printStackTrace();
		}
	}

	public void showButtonFragment() {
		CreditButtonFragment bFrag = new CreditButtonFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.layout_container_credit_note_b, bFrag);
		ft.commit();
	}

	public ArrayList<ProductItem> getList() {
		return list;
	}

	public void message(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public double getTot() {
		return tot;
	}

	private void getCreditTotal() {
		tot = 0;
		if (list != null) {
			for (ProductItem temp : list) {
				tot = tot + (temp.getQuantity() * temp.getPriceSell());
			}
		}
		setTotDisplay();
	}

	public void clearList() {
		list = null;
		setListAdapter(null);
	}

	public void setList(ArrayList<ProductItem> i) {
		this.list = i;
		itemAdapter = new ProductItemAdapter(this, R.id.list_item, list);
		setListAdapter(itemAdapter);
		getCreditTotal();
	}

	public void setTotDisplay() {
		mTextViewTotal.setText("R " + tot);
	}
}
