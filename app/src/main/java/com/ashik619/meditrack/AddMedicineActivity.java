package com.ashik619.meditrack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ashik619.meditrack.custom_view.CustomDialog;
import com.ashik619.meditrack.custom_view.CustomEditText;
import com.ashik619.meditrack.custom_view.CustomTextView;
import com.ashik619.meditrack.models.Medicine;
import com.ashik619.meditrack.models.Time;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddMedicineActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toggleBtnDW)
    ToggleSwitch toggleBtnDW;
    @BindView(R.id.daySelector)
    ToggleSwitch daySelector;
    ArrayList<String> labels = new ArrayList<>();
    @BindView(R.id.nameText)
    CustomEditText nameText;
    @BindView(R.id.doseText)
    CustomEditText doseText;
    @BindView(R.id.numberText)
    CustomEditText numberText;
    @BindView(R.id.timesetBtn)
    Button timesetBtn;
    @BindView(R.id.craeteBtn)
    Button craeteBtn;
    int day  = 0;
    int doseNum = 0;
    ArrayList<Time> reminderList;
    Realm myRealm;
    @BindView(R.id.quantiyText)
    CustomEditText quantiyText;
    @BindView(R.id.timeText)
    CustomTextView timeText;
    private String name, quantity, puchasedNo;
    boolean isDaily;
    boolean isEdit;
    Medicine eMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit",false);
        if(isEdit){
            prepopulateData(intent);
        }
        initiate();
        toggleBtnDW.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 1 && isChecked) {
                    daySelector.setVisibility(View.VISIBLE);
                } else daySelector.setVisibility(View.GONE);
            }
        });
        timesetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doseNum = Integer.parseInt(doseText.getText().toString());
                if (reminderList.size() < doseNum) {
                    pickTime();
                }
            }
        });
        craeteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addMedicine();
            }
        });
        doseText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                //System.out.println("before" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
               // doseNum = Integer.parseInt(doseText.getText().toString());

            }
        });

    }
    void prepopulateData(Intent intent){
        nameText.setText(intent.getStringExtra("name"), TextView.BufferType.EDITABLE);
        doseText.setText(intent.getStringExtra("dose"), TextView.BufferType.EDITABLE);
        quantiyText.setText(intent.getStringExtra("quantity"), TextView.BufferType.EDITABLE);

    }

    void initiate() {
        Realm.init(AddMedicineActivity.this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("medicines.realm")
                .build();
        myRealm = Realm.getInstance(config);
        reminderList = new ArrayList<>();
        labels.add("Sun");
        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thu");
        labels.add("Fri");
        labels.add("Sat");
        daySelector.setLabels(labels);
    }

    void pickTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddMedicineActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Log.e("TAg", "" + selectedHour + selectedMinute);

                Time time = new Time(selectedHour, selectedMinute);
                reminderList.add(time);
                if (reminderList.size() < doseNum) {
                    timesetBtn.setText("set reminder " + (reminderList.size() + 1));
                    timeText.append("\nReminder at "+selectedHour+":"+selectedMinute);
                }

            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    void createAlarm(int hour, int min, int day) {
        name = nameText.getText().toString();
        quantity = quantiyText.getText().toString();
        if (name.equals("")) {
            Toast.makeText(AddMedicineActivity.this, "Enter Name of medicine", Toast.LENGTH_SHORT).show();
            return;
        }
        if (quantity.equals("")) {
            Toast.makeText(AddMedicineActivity.this, "Enter Quantity of medicine", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent myIntent = new Intent(AddMedicineActivity.this, NotificationReciever.class);
        myIntent.putExtra("name",name);
        myIntent.putExtra("quant",quantity);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddMedicineActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 00);
        if(!isDaily){
            calendar.set(Calendar.DAY_OF_WEEK,day+1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 7*60 * 60 * 1000, pendingIntent);
        }else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent); //Repeat every 24 hours
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    void addMedicine() {

        name = nameText.getText().toString();
        quantity = quantiyText.getText().toString();
        puchasedNo = numberText.getText().toString();
        doseNum = Integer.parseInt(doseText.getText().toString());
        if(reminderList.size()<doseNum){
            Toast.makeText(AddMedicineActivity.this, "Add reminder", Toast.LENGTH_SHORT).show();
            return;
        }

        if (toggleBtnDW.getCheckedTogglePosition() == 0) {
            isDaily = true;
        } else {
            isDaily = false;
            day = daySelector.getCheckedTogglePosition();
        }
        if (name.equals("")) {
            Toast.makeText(AddMedicineActivity.this, "Enter Name of medicine", Toast.LENGTH_SHORT).show();
            return;
        }
        if (quantity.equals("")) {
            Toast.makeText(AddMedicineActivity.this, "Enter Quantity of medicine", Toast.LENGTH_SHORT).show();
            return;
        }
        if (doseNum == 0) {
            Toast.makeText(AddMedicineActivity.this, "Dose cannot be zero", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Time time : reminderList) {
            createAlarm(time.hour, time.min, 10);
        }
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Medicine medicine = myRealm.createObject(Medicine.class, name);
                Medicine medicine = new Medicine();
                medicine.setName(name);
                medicine.setDose(String.valueOf(doseNum));
                medicine.setQuantity(quantity);
                medicine.setPurchasedNo(puchasedNo);
                medicine.setDay(day);
                String timestr = "";
                for (Time time : reminderList) {
                    timestr = timestr + time.hour + ":" + time.min + " ";
                }
                medicine.setTime(timestr);
                medicine.setDaily(isDaily);
                realm.insertOrUpdate(medicine);
            }
        });
        showDialog();


    }
    void showDialog(){
        final CustomDialog dialog=new CustomDialog(this);
        dialog.show();
        dialog.addTitle("Expiry Date");
        dialog.setCancelable(false);
        dialog.setMessage("Please check expiry date of medicine");
        dialog.isCancelable(false);
        dialog.onPrimaryClick("OK", new CustomDialog.OnClickCallback() {
            @Override
            public void clicked(Object... data) {
                dialog.dismiss();
                Intent intent = new Intent(AddMedicineActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
