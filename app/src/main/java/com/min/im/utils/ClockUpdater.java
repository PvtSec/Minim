package com.min.im.utils;

import android.annotation.SuppressLint;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.os.Handler;
import android.os.Looper;

public class ClockUpdater {
    @SuppressLint("StaticFieldLeak")
    private static ClockUpdater instance;
    private final Handler handler;
    private final TextView timeField;
    private final TextView dateField;

    public static Map<String, String> getDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = timeFormat.format(calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        Map<String, String> dateTime = new HashMap<>();
        dateTime.put("time", currentTime);
        dateTime.put("date", currentDate);

        return dateTime;
    }

    private ClockUpdater(TextView timeField, TextView dateField) {
        this.timeField = timeField;
        this.dateField = dateField;
        this.handler = new Handler(Looper.getMainLooper());
        startUpdatingTime();
    }

    public static ClockUpdater getInstance(TextView timeField, TextView dateField) {
        if (instance == null) {
            instance = new ClockUpdater(timeField, dateField);
        }
        return instance;
    }

    private void startUpdatingTime() {
        updateDateTime();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateDateTime();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void updateDateTime() {
        Map<String, String> dateTime = ClockUpdater.getDateTime();
        timeField.setText(dateTime.get("time"));
        dateField.setText(dateTime.get("date"));
    }

    public void stopUpdatingTime() {
        handler.removeCallbacksAndMessages(null);
    }
}