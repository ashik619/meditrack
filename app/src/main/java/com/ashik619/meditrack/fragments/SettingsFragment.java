package com.ashik619.meditrack.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashik619.meditrack.AddMedicineActivity;
import com.ashik619.meditrack.MeditrackApp;
import com.ashik619.meditrack.R;
import com.ashik619.meditrack.SosActivity;
import com.ashik619.meditrack.custom_view.CustomTextView;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    @BindView(R.id.addMedicineBtn)
    CardView addMedicineBtn;
    @BindView(R.id.nameText)
    CustomTextView nameText;
    @BindView(R.id.ageText)
    CustomTextView ageText;
    @BindView(R.id.sosBtn)
    CardView sosBtn;
    BottomBar bottomBar;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        bottomBar = (BottomBar)getActivity().findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(2, false);
        nameText.setText(MeditrackApp.getLocalPrefInstance().getName());
        ageText.setText(MeditrackApp.getLocalPrefInstance().getAge());
        addMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddMedicineActivity.class);
                startActivity(intent);
            }
        });
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SosActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
