package com.ashik619.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ashik619.meditrack.custom_view.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nameText)
    CustomEditText nameText;
    @BindView(R.id.quantiyText)
    CustomEditText quantiyText;
    @BindView(R.id.craeteBtn)
    Button craeteBtn;
    boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        if(intent1.getBooleanExtra("isEdit",false)){
            isEdit = true;
        }else if (MeditrackApp.getLocalPrefInstance().getName()!= null){
            Intent intent  = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(isEdit){
            nameText.setText(MeditrackApp.getLocalPrefInstance().getName(), TextView.BufferType.EDITABLE);
            quantiyText.setText(MeditrackApp.getLocalPrefInstance().getAge(), TextView.BufferType.EDITABLE);
        }
        craeteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }
    void create(){
        String name =nameText.getText().toString();
        String age = quantiyText.getText().toString();
        if(name.equals("")){
            Toast.makeText(LoginActivity.this, "Enter name",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(age.equals("")){
            Toast.makeText(LoginActivity.this, "Enter age",
                    Toast.LENGTH_LONG).show();
            return;
        }
        MeditrackApp.getLocalPrefInstance().setName(name);
        MeditrackApp.getLocalPrefInstance().setAge(age);
        Intent intent  = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
