package com.ashik619.meditrack.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ashik619.meditrack.AddMedicineActivity;
import com.ashik619.meditrack.R;
import com.ashik619.meditrack.adapter.HomeListAdapter;
import com.ashik619.meditrack.adapter.MedicineListAdapter;
import com.ashik619.meditrack.custom_view.CustomEditText;
import com.ashik619.meditrack.helper.DoneOnEditorActionListener;
import com.ashik619.meditrack.models.Medicine;
import com.roughike.bottombar.BottomBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicinesFragment extends Fragment {


    @BindView(R.id.listView)
    ListView listView;
    Realm myRealm;
    CustomEditText searchText;

    public MedicinesFragment() {
        // Required empty public constructor
    }
    MedicineListAdapter medicineListAdapter;
    BottomBar bottomBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicines, container, false);
        bottomBar = (BottomBar)getActivity().findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(1, false);
        ButterKnife.bind(this, view);
        Realm.init(getActivity().getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("medicines.realm")
                .build();
        myRealm = Realm.getInstance(config);

        populateListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                Medicine medicine = (Medicine) listView.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), AddMedicineActivity.class);
                intent.putExtra("isEdit",true);
                intent.putExtra("name",medicine.getName());
                intent.putExtra("dose",medicine.getDose());
                intent.putExtra("quantity",medicine.getQuantity());
                startActivity(intent);
            }
        });
        searchText = (CustomEditText) getActivity().findViewById(R.id.searchText);
        searchText.setOnEditorActionListener(new DoneOnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    doSearch(searchText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        return view;
    }
    void doSearch(String text){
        if(text.length()==0){
            populateListView();
        }else getSearchResults(text);

    }
    void populateListView(){
        RealmResults<Medicine> realmResults = myRealm.where(Medicine.class).findAll();
        medicineListAdapter = new MedicineListAdapter(getActivity().getApplicationContext(),realmResults);
        listView.setAdapter(medicineListAdapter);
    }
    void getSearchResults(String text){
        RealmResults<Medicine> realmResults = myRealm.where(Medicine.class).contains("name", text, Case.INSENSITIVE).findAll();
        medicineListAdapter = new MedicineListAdapter(getActivity().getApplicationContext(),realmResults);
        listView.setAdapter(medicineListAdapter);
    }

}
