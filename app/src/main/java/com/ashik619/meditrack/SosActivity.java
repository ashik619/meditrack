package com.ashik619.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ashik619.meditrack.custom_view.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SosActivity extends AppCompatActivity {

    @BindView(R.id.nameText)
    CustomEditText nameText;
    @BindView(R.id.craeteBtn)
    Button craeteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        ButterKnife.bind(this);
        craeteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }
    void create(){
        String name =nameText.getText().toString();

        if(name.equals("")){
            Toast.makeText(SosActivity.this, "Enter phone number",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(name.length()<10){
            Toast.makeText(SosActivity.this, "Enter valid phone number",
                    Toast.LENGTH_LONG).show();
            return;
        }

        MeditrackApp.getLocalPrefInstance().setPhone(name);

        Intent intent  = new Intent(SosActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}
