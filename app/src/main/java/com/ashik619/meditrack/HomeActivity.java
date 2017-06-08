package com.ashik619.meditrack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashik619.meditrack.custom_view.CustomEditText;
import com.ashik619.meditrack.custom_view.CustomTextView;
import com.ashik619.meditrack.fragments.HomeFragment;
import com.ashik619.meditrack.fragments.MedicinesFragment;
import com.ashik619.meditrack.fragments.SettingsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.sosBtn)
    CustomTextView sosBtn;
    @BindView(R.id.searchText)
    CustomEditText searchText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    HomeFragment homeFragment;
    MedicinesFragment medicinesFragment;
    SettingsFragment settingsFragment;
    String phoneNumber;
    int previousTab = R.id.home1;

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = MeditrackApp.getLocalPrefInstance().getPhone();
                if(phoneNumber!=null){
                    checkCallPermission();
                }else Toast.makeText(HomeActivity.this, "Go to SOS setting and enter phone number",
                        Toast.LENGTH_LONG).show();
            }
        });
        inflateHome();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.home1 :
                        searchText.setVisibility(View.GONE);
                        inflateHome();
                        break;
                    case R.id.settings :
                        searchText.setVisibility(View.GONE);
                       inflateSettings();
                       break;
                    case R.id.medicines :
                        searchText.setVisibility(View.VISIBLE);
                        inflateMedicine();
                        break;
                }
            }
        });
    }
    void inflateHome(){
        homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction homeTransaction = manager.beginTransaction();
        homeTransaction.replace(R.id.container, homeFragment);
        homeTransaction.addToBackStack(null);
        homeTransaction.commit();
    }
    void inflateMedicine(){
        medicinesFragment = new MedicinesFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction homeTransaction = manager.beginTransaction();
        homeTransaction.replace(R.id.container, medicinesFragment);
        homeTransaction.addToBackStack(null);
        homeTransaction.commit();
    }
    void inflateSettings(){
        settingsFragment = new SettingsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction homeTransaction = manager.beginTransaction();
        homeTransaction.replace(R.id.container, settingsFragment);
        homeTransaction.addToBackStack(null);
        homeTransaction.commit();
    }


    void checkCallPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(HomeActivity.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            }else makeCall();
        }else makeCall();
    }
    void makeCall(){
        Log.d("call","starting");
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }
    void requestPermission(){
        ActivityCompat.requestPermissions(HomeActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    requestPermission();
                }

            }

        }
    }
}
