package com.icydevs.pixelpos;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.icydevs.designtemp.R;

public class InvoiceActivity extends ListActivity {

	ProductItemAdapter itemAdapter;
	ListView mListView;
	ArrayList<ProductItem> list;
	double tot;
	TextView mTextViewTotal;
	View v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice);
		
		SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_prefs), Context.MODE_PRIVATE);
		boolean stayLoggedIn = prefs.getBoolean("stayLoggedIn", false);
		

		mListView = (ListView) findViewById(android.R.id.list);
		mTextViewTotal = (TextView) findViewById(R.id.text_view_total_2);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				EditItemDialogFragment dialog = new EditItemDialogFragment();
				dialog.setStyle(Window.FEATURE_NO_TITLE, 0);
				dialog.show(getFragmentManager(), "EditItemDialogFragment");
				dialog.setPosition(pos);
			}
		});

		showButtonFragment();
		list = new ArrayList<ProductItem>();
	}

	public void showButtonFragment() {
		SaleButtonFragment bFrag = new SaleButtonFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.layout_container_sales_b, bFrag);
		ft.commit();
	}

	public double getTotal() {		
		return tot;
	}

	public void setTotDisplay() {
		mTextViewTotal.setText("R " + tot);
	}

	public void addListItem(ProductItem listTemp) {
		if (listTemp != null) {
			list.add(listTemp);
			getTotal();
		}
		try {
			itemAdapter = new ProductItemAdapter(this, R.id.list_item, list);
			setListAdapter(itemAdapter);

		} catch (Exception e) {
			Log.d("-- ListView --",
					"Could not display ListView in InvoiceActivity");
			e.printStackTrace();
		}
	}

	private void getInvoiceTotal() {
		tot = 0;
		if (list != null) {
			for (ProductItem temp : list) {
				tot = tot + (temp.getQuantity() * temp.getPriceSell());
			}
		}
		setTotDisplay();
	}

	public ArrayList<ProductItem> getList() {
		return list;
	}

	public void message(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public void setList(ArrayList<ProductItem> i) {
		this.list = i;
			itemAdapter = new ProductItemAdapter(this, R.id.list_item, list);
			setListAdapter(itemAdapter);
			getInvoiceTotal();
	}

	public void clearList() {
		list = null;
		setListAdapter(null);
	}
	
	public void setTotal(double d) {
		this.tot = d;
		getTotal();
	}

}