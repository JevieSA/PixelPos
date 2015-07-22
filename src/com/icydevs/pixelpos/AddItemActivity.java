package com.icydevs.pixelpos;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.icydevs.designtemp.R;

public class AddItemActivity extends ListActivity {

	ListView mListView;
	ArrayList<ProductItem> list;
	ProductItemAdapter itemAdapter;
	PixelDBAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		showButtonFragment();
		list = new ArrayList<ProductItem>();
		adapter = new PixelDBAdapter(this);
	}

	public void showButtonFragment() {
		ScanButtonFragment bFrag = new ScanButtonFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.layout_container_add_item_c, bFrag);
		ft.commit();
	}

	public ArrayList<ProductItem> getList() {
		return list;
	}

	public void showList(ProductItem listTemp) {
		if (listTemp != null) {
			list.add(listTemp);
		}
		try {
			itemAdapter = new ProductItemAdapter(this, R.id.list_item, list);
			setListAdapter(itemAdapter);
		} catch (Exception e) {
			Log.d("-- ListView --",
					"Could not display ListView in AddItemActivity");
			e.printStackTrace();
		}
	}

	public void clearList() {
		list = null;
		setListAdapter(null);
	}
}
