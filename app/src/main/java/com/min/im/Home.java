package com.min.im;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.min.im.utils.ClockUpdater;
import com.min.im.utils.BatteryUpdater;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private ClockUpdater clockUpdater;
    private BatteryUpdater batteryUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_home);

        TextView timeField = findViewById(R.id.time_field);
        TextView dateField = findViewById(R.id.date_field);
        View batteryStatus = findViewById(R.id.batteryStatus);

        clockUpdater = ClockUpdater.getInstance(timeField, dateField);
        batteryUpdater = BatteryUpdater.getInstance(getApplicationContext(), batteryStatus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clockUpdater.stopUpdatingTime();
        batteryUpdater.stopUpdatingBattery();
    }
}