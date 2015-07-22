package com.icydevs.pixelpos;

import java.util.ArrayList;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icydevs.designtemp.R;

public class EditItemDialogFragment extends DialogFragment implements
		View.OnClickListener {

	Button mButtonCancel, mButtonDelete, mButtonConfirm;
	EditText mEditQuant, mEditDisc;
	int var, position;
	ProductItem m_item;
	ArrayList<ProductItem> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_edit_item, null);

		mButtonCancel = (Button) v.findViewById(R.id.button_cancel);
		mButtonConfirm = (Button) v.findViewById(R.id.button_confirm);
		mButtonDelete = (Button) v.findViewById(R.id.button_delete);

		mButtonCancel.setOnClickListener(this);
		mButtonConfirm.setOnClickListener(this);
		mButtonDelete.setOnClickListener(this);

		mEditDisc = (EditText) v.findViewById(R.id.edit_text_disc);
		mEditQuant = (EditText) v.findViewById(R.id.edit_text_quant);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_cancel:
			dismiss();
			break;
		case R.id.button_confirm:
			InvoiceActivity act_conf = ((InvoiceActivity) getActivity());
			list = act_conf.getList();
			boolean editValid = false;

			if (!mEditDisc.getText().toString().equals("")) {
				double disc = Double.parseDouble(mEditDisc.getText()
						.toString());
				if (disc <= 100 && disc >= 0) {
					double discountTot = list.get(position).getPriceSell()
							- (list.get(position).getPriceSell() * (disc / 100));
					list.get(position).setPriceSell(discountTot);
					act_conf.getTotal();
					editValid = true;
				} else {
					Toast.makeText(getActivity(), "Discount invalid",
							Toast.LENGTH_SHORT).show();
				}
			}
			if (!mEditQuant.getText().toString().equals("")) {
				int quant = Integer.parseInt(mEditQuant.getText()
						.toString());
				list.get(position).setQuantity(quant);
				list.get(position).setPriceSell(
						quant * list.get(position).getPriceSell());
				editValid = true;
			}

			if (editValid) {
				act_conf.setList(list);
				dismiss();
			} else {
				Toast.makeText(getActivity(), "No data entered.",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.button_delete:
			InvoiceActivity act_del = ((InvoiceActivity) getActivity());
			list = act_del.getList();
			list.remove(position);
			act_del.setList(list);
			dismiss();
			break;
		}
	}

	public void setVar(int i) {
		this.var = i;
	}

	public void setPosition(int i) {
		this.position = i;
	}
}
