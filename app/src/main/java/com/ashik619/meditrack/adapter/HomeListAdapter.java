package com.ashik619.meditrack.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.ashik619.meditrack.Constants;
import com.ashik619.meditrack.R;
import com.ashik619.meditrack.custom_view.CustomTextView;
import com.ashik619.meditrack.models.Medicine;

import java.util.Calendar;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by ashik619 on 06-06-2017.
 */
public class HomeListAdapter extends RealmBaseAdapter<Medicine> implements ListAdapter {
    RealmResults<Medicine> realmResults;
    Context context;
    public HomeListAdapter(Context context, RealmResults<Medicine> realmResults){
        super(realmResults);
        this.realmResults = realmResults;
        this.context = context;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Medicine medicine = realmResults.get(i);
        if(medicine.isDaily || ((medicine.getDay()+1) == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) ) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.home_list_item, viewGroup, false);
            }
            CustomTextView medName = (CustomTextView) view.findViewById(R.id.nameText);
            CustomTextView quantity = (CustomTextView) view.findViewById(R.id.amountText);
            CustomTextView time = (CustomTextView) view.findViewById(R.id.timeText);

            medName.setText(medicine.getName());
            quantity.setText("Dose amount: " + medicine.getQuantity());
            time.setText("Number of Times : " + medicine.getDose() + " at " + medicine.getTime());
            return view;
        }else return view;
    }
}
