package com.maxis7567.msdatepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Date;

public class MsDatePicker {

    private final NumberPicker day;
    private final NumberPicker month;
    private final NumberPicker year;
    private final String EdayFa;
    private final String EmonthFa;
    private final String EyearFa;
    private final String SdayFa;
    private final String SmonthFa;
    private final String SyearFa;


    public MsDatePicker(final ViewGroup viewGroup, Context context, long startUnix, final long endUnix, @Nullable String title, @Nullable String positiveButton, @Nullable String negativeButton, final MsDatePickerEvents events) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.datepicker, viewGroup, false);
        String newDate = JalaliCalendar.gregorian_to_jalali(String.valueOf(endUnix / 1000), JalaliCalendar.TYPE_DATE);
        EyearFa = newDate.substring(0, 4);
        EmonthFa = newDate.substring(5, 7);
        EdayFa = newDate.substring(8, 10);
        newDate = JalaliCalendar.gregorian_to_jalali(String.valueOf(startUnix / 1000), JalaliCalendar.TYPE_DATE);
        SyearFa = newDate.substring(0, 4);
        SmonthFa = newDate.substring(5, 7);
        SdayFa = newDate.substring(8, 10);
        year = view.findViewById(R.id.number_pickerYear);
        year.setMaxValue(Integer.parseInt(EyearFa));
        year.setMinValue(Integer.parseInt(SyearFa));
        month = view.findViewById(R.id.number_pickerMonth);
        month.setMaxValue(Integer.parseInt(EmonthFa));
        month.setMinValue(Integer.parseInt(SmonthFa));
        day = view.findViewById(R.id.number_pickerDay);
        day.setMaxValue(Integer.parseInt(EdayFa));
        day.setMinValue(Integer.parseInt(SdayFa));
        day.setValue(Integer.parseInt(EdayFa));
        month.setValue(Integer.parseInt(EmonthFa));
        year.setValue(Integer.parseInt(EyearFa));
        yearHandler(Integer.parseInt(EyearFa));
//        String[] data = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
//        month.setDisplayedValues(data);
        year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearHandler(newVal);
            }
        });
        month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                monthHandler(newVal);
            }
        });
        year.setFormatter("%02d");
        month.setFormatter("%02d");
        day.setFormatter("%02d");
        year.setTypeface(Typeface.createFromAsset(context.getAssets(), "bold.ttf"));
        year.setSelectedTypeface(Typeface.createFromAsset(context.getAssets(), "bold.ttf"));
        month.setTypeface(Typeface.createFromAsset(context.getAssets(), "bold.ttf"));
        month.setSelectedTypeface(Typeface.createFromAsset(context.getAssets(), "bold.ttf"));
        day.setTypeface(Typeface.createFromAsset(context.getAssets(), "bold.ttf"));
        day.setSelectedTypeface(Typeface.createFromAsset(context.getAssets(), "bold.ttf"));
        if(title!=null){
            ((TextView) view.findViewById(R.id.DatePickerTitle)).setText(title);
        }else {
            (view.findViewById(R.id.DatePickerTitle)).setVisibility(View.GONE);
        }
        if (positiveButton!=null){
            ((TextView) view.findViewById(R.id.Positive)).setText(positiveButton);
        }
        if (negativeButton!=null){
            ((TextView) view.findViewById(R.id.Negative)).setText(positiveButton);
        }
        view.findViewById(R.id.Positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewGroup.removeView(view);
                }catch (Exception ignore){

                }


                events.onConfirm(new int[]{year.getValue(),month.getValue(),day.getValue()},JalaliCalendar.jalali_to_unixDate(year.getValue(),month.getValue(),day.getValue()));
            }
        });
        view.findViewById(R.id.Negative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewGroup.removeView(view);
                }catch (Exception ignore){

                }
                events.onCancel();
            }
        });
        try {
            viewGroup.addView(view);
        }catch (Exception ignore){

        }
    }

    private void monthHandler(int month) {
        int temp = day.getValue();
        if (year.getValue() == Integer.parseInt(SyearFa)&&month==Integer.parseInt(SmonthFa)) {
            if (month >= 7) {
                day.setMaxValue(30);
                day.setMinValue(Integer.parseInt(SdayFa));
                if (temp == 31 || temp < day.getMinValue()) {
                    day.setValue(day.getMinValue());
                } else {
                    day.setValue(temp);
                }
            } else {
                day.setMaxValue(31);
                day.setMinValue(Integer.parseInt(SdayFa));
                if (temp < day.getMinValue()) {
                    day.setValue(day.getMaxValue());
                } else day.setValue(temp);
            }
        } else if (year.getValue() == Integer.parseInt(EyearFa)&&month==Integer.parseInt(SmonthFa)) {
            if (month >= 7) {
                if (31==Integer.parseInt(EdayFa)){
                 day.setMaxValue(30);
                }else {
                    day.setMaxValue(Integer.parseInt(EdayFa));
                }
                day.setMinValue(1);
                if (temp > day.getMaxValue()) {
                    day.setValue(day.getMaxValue());
                } else {
                    day.setValue(temp);
                }
            } else {
                day.setMaxValue(Integer.parseInt(EdayFa));
                day.setMinValue(1);
                if (temp > day.getMaxValue()) {
                    day.setValue(day.getMaxValue());
                } else day.setValue(temp);
            }
        } else {
            if (month >= 7) {
                day.setMaxValue(30);
                day.setMinValue(1);
                if (temp == 31) {
                    day.setValue(1);
                } else {
                    day.setValue(temp);
                }
            } else {
                day.setMaxValue(31);
                day.setMinValue(1);
                day.setValue(temp);
            }
        }
    }

    private void yearHandler(int year) {
        int temp;
        if (year == Integer.parseInt(SyearFa)) {
            temp = month.getValue();
            month.setMinValue(Integer.parseInt(SmonthFa));
            month.setMaxValue(12);
            if (temp >= month.getMinValue()) {
                month.setValue(temp);
            } else {
                month.setValue(Integer.parseInt(SmonthFa));
            }
            temp = day.getValue();
            day.setMinValue(Integer.parseInt(SdayFa));
            if (month.getValue() >= 7) {
                day.setMaxValue(30);
                if (temp == 31 || temp < day.getMinValue()) {
                    day.setValue(day.getMinValue());
                } else day.setValue(temp);
            } else {
                day.setMaxValue(31);
                if (temp >= day.getMinValue()) {
                    day.setValue(temp);
                } else day.setValue(Integer.parseInt(SdayFa));
            }
        } else if (year == Integer.parseInt(EyearFa)) {
            temp = month.getValue();
            month.setMaxValue(Integer.parseInt(EmonthFa));
            month.setMinValue(1);
            if (temp <= month.getMaxValue()) {
                month.setValue(temp);
                temp = day.getValue();
                if (month.getValue() >= 7) {
                    if (Integer.parseInt(EdayFa) == 31) {
                        day.setMaxValue(30);
                    } else {
                        day.setMaxValue(Integer.parseInt(EdayFa));
                    }
                    day.setMinValue(1);
                    if (temp == 31 || temp > day.getMaxValue()) {
                        day.setValue(1);
                    }
                } else {
                    day.setMaxValue(Integer.parseInt(EdayFa));
                    day.setMinValue(1);
                    if (temp > day.getMaxValue()) {
                        day.setValue(1);
                    } else {
                        day.setValue(temp);
                    }
                }
            } else {
                temp = day.getValue();
                month.setValue(1);
                day.setMaxValue(Integer.parseInt(EdayFa));
                day.setMinValue(1);
                if (temp > day.getMaxValue()) {
                    day.setValue(1);
                } else {
                    day.setValue(temp);
                }
            }
        } else {
            temp = month.getValue();
            month.setMinValue(1);
            month.setMaxValue(12);
            month.setValue(temp);
            if (temp >= 7) {
                temp = day.getValue();
                day.setMaxValue(30);
                day.setMinValue(1);
                if (temp != 31) {
                    day.setValue(temp);
                }
            } else {
                temp = day.getValue();
                day.setMaxValue(31);
                day.setMinValue(1);
                day.setValue(temp);
            }
        }
    }
}
