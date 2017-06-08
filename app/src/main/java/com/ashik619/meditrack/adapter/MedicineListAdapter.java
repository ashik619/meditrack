package com.ashik619.meditrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.ashik619.meditrack.R;
import com.ashik619.meditrack.custom_view.CustomTextView;
import com.ashik619.meditrack.models.Medicine;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by ashik619 on 06-06-2017.
 */
public class MedicineListAdapter extends RealmBaseAdapter<Medicine> implements ListAdapter {
    RealmResults<Medicine> realmResults;
    Context context;
    public MedicineListAdapter(Context context, RealmResults<Medicine> realmResults){
        super(realmResults);
        this.realmResults = realmResults;
        this.context = context;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.medicine_list_item, viewGroup, false);
        }
        CustomTextView medName = (CustomTextView) view.findViewById(R.id.nameText);
        Medicine medicine = realmResults.get(i);
        medName.setText(medicine.getName());
        return view;
    }
}
