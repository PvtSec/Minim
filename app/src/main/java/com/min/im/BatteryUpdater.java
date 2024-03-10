package com.min.im;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class BatteryUpdater {
    private static BatteryUpdater instance;
    private final Handler handler;
    private final Context context;
    private final View batteryStatus;
    private final int screenWidth;

    private BatteryUpdater(Context context, View batteryStatus) {
        this.batteryStatus = batteryStatus;
        this.context = context.getApplicationContext();
        this.handler = new Handler(Looper.getMainLooper());
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        startUpdatingBattery();
    }

    public static BatteryUpdater getInstance(Context context, View batteryStatus) {
        if (instance == null) {
            instance = new BatteryUpdater(context, batteryStatus);
        }
        return instance;
    }

    private int getBatteryPercent() {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        if (batteryManager != null) {
            return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        return -1;
    }

    private void startUpdatingBattery() {
        updateBattery();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateBattery();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void updateBattery() {
        int batteryPercentage = getBatteryPercent();
        batteryStatus.getLayoutParams().width = (screenWidth * batteryPercentage) / 100;
        batteryStatus.requestLayout();
    }

    public void stopUpdatingBattery() {
        handler.removeCallbacksAndMessages(null);
    }
}