package com.ashik619.meditrack.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ashik619.meditrack.R;
import com.ashik619.meditrack.adapter.HomeListAdapter;
import com.ashik619.meditrack.models.Medicine;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.listView)
    ListView listView;
    Realm myRealm;
    BottomBar bottomBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    HomeListAdapter homeListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        bottomBar = (BottomBar)getActivity().findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(0, false);
        Realm.init(getActivity().getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("medicines.realm")
                .build();
        myRealm = Realm.getInstance(config);
        RealmResults<Medicine> realmResults = myRealm.where(Medicine.class).findAll();
        homeListAdapter = new HomeListAdapter(getActivity().getApplicationContext(),realmResults);
        listView.setAdapter(homeListAdapter);
        return view;
    }

}
