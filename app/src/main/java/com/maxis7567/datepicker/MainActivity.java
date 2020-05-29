package com.maxis7567.datepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.maxis7567.msdatepicker.MsDatePicker;
import com.maxis7567.msdatepicker.MsDatePickerEvents;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long end= Calendar.getInstance().getTimeInMillis();
        Calendar s=Calendar.getInstance();
        s.add(Calendar.YEAR,-3);
        long start=s.getTimeInMillis();
        //@start= 3 years ago
        //@end= now
        new MsDatePicker((ViewGroup) findViewById(R.id.View), MainActivity.this, start, end, null, null, null, new MsDatePickerEvents() {
            @Override
            public void onConfirm(int[] jalali,Date date) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
