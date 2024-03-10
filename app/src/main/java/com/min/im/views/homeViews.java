package com.min.im.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.im.R;
import com.min.im.utils.ClockUpdater;
import com.min.im.utils.BatteryUpdater;
import androidx.fragment.app.Fragment;

public class homeViews extends Fragment {
    private ClockUpdater clockUpdater;
    private BatteryUpdater batteryUpdater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_views, container, false);

        TextView timeField = view.findViewById(R.id.time_field);
        TextView dateField = view.findViewById(R.id.date_field);
        View batteryStatus = view.findViewById(R.id.batteryStatus);

        clockUpdater = ClockUpdater.getInstance(timeField, dateField);
        batteryUpdater = BatteryUpdater.getInstance(getContext(), batteryStatus);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clockUpdater.stopUpdatingTime();
        batteryUpdater.stopUpdatingBattery();
    }
}