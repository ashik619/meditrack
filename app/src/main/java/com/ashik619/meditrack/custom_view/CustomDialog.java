package com.ashik619.meditrack.custom_view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashik619.meditrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ashik619 on 29/8/16.
 */

public class CustomDialog extends Dialog {

    @BindView(R.id.titleText)
    TextView titleText;

    @BindView(R.id.infoText)
    TextView infoText;

    @BindView(R.id.primaryButton)
    LinearLayout buttonPrimary;


    @BindView(R.id.primaryText)
    CustomTextView primaryText;




    public CustomDialog(Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_main);
        ButterKnife.bind(this);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public void isCancelable(boolean cancelable) {
        setCancelable(cancelable);

    }


    public void setMessage(String message) {
        infoText.setText(message);
    }

    public void setMessageResource(int res_id) {
        infoText.setText(res_id);
    }

    public void addTitle(String title) {
        titleText.setText(title);
    }

    public void onPrimaryClick(String buttonText, final OnClickCallback callback) {
        primaryText.setText(buttonText);
        buttonPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clicked("Cancelled");
            }
        });
    }



    public void showDialog() {
        this.show();
    }

    public interface OnClickCallback {
        void clicked(Object... data);
    }


    //To implement use

   /* final CustomDialog dialog=new CustomDialog(this);
    dialog.show();
    dialog.addTitle("Both Button");
    dialog.setCancelable(false);
    dialog.setMessage("Click to cancel");
    dialog.isCancelable(false);
    dialog.onPrimaryClick("Cancel", new CustomDialog.OnClickCallback() {
        @Override
        public void clicked(Object... data) {
            Toast.makeText(UserTypeActivity.this, (String)data[0], Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    });
    dialog.onSecondaryClick("Ok", new CustomDialog.OnClickCallback() {
        @Override
        public void clicked(Object... data) {
            Toast.makeText(UserTypeActivity.this, (String)data[0], Toast.LENGTH_SHORT).show();
        }
    });*/
}
