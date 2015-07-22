package com.icydevs.pixelpos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.icydevs.designtemp.R;

public class ScanButtonFragment extends Fragment implements View.OnClickListener{
	
	Button mButtonFinalize, mButtonAddItem, mButtonBack, mButtonCreateItem;
	PixelDBAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_scan_button, container, false);
			
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		mButtonAddItem = (Button)getActivity().findViewById(R.id.scan_button_add_item);
		mButtonBack = (Button)getActivity().findViewById(R.id.scan_button_back);
		mButtonFinalize = (Button)getActivity().findViewById(R.id.scan_button_finalize);
		mButtonCreateItem = (Button)getActivity().findViewById(R.id.scan_button_create_item);
		
		mButtonAddItem.setOnClickListener(this);
		mButtonBack.setOnClickListener(this);
		mButtonFinalize.setOnClickListener(this);
		mButtonCreateItem.setOnClickListener(this);
		
		adapter = new PixelDBAdapter(getActivity());
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{		
		case R.id.scan_button_back:
			Intent intent = new Intent(getActivity(), MainActivity.class);
			intent.putExtra("restart", true);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.scan_button_create_item:
			CreateItemDialog dialog = new CreateItemDialog();
			FragmentManager manager = getFragmentManager();
			dialog.setStyle(Window.FEATURE_NO_TITLE, 0);
			dialog.show(manager, "CreateDialogFragment");
			break;
		case R.id.scan_button_add_item:
			SearchDialogFragment myDialog = new SearchDialogFragment();
			FragmentManager sManager = getFragmentManager();
			myDialog.setStyle(Window.FEATURE_NO_TITLE, 0);
			myDialog.show(sManager, "AddItemSearch");
			break;
		case R.id.scan_button_finalize:
			AddItemActivity act = (AddItemActivity) getActivity();
			adapter.addNewItems(act.getList());
			act.clearList();
			break;
		}
		
	}

}
