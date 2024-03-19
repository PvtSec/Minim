package com.min.im.views;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import com.min.im.R;
import com.min.im.adapters.AppListAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class appListViews extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        WindowManager windowManager = (WindowManager) requireContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        View view = inflater.inflate(R.layout.applist_views, container, false);

        EditText search_field = view.findViewById(R.id.search_field);
        search_field.getLayoutParams().width = (int) (displayMetrics.widthPixels * 0.8);

        RecyclerView recyclerView = view.findViewById(R.id.appListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        AppListAdapter adapter = new AppListAdapter(getContext(), getAllApps());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private Map<String, Intent> getAllApps() {
        Map<String, Intent> launchableAppMap = new TreeMap<>();
        PackageManager pm = requireContext().getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(0);
        for (ApplicationInfo app : appList) {
            Intent appLauncher = pm.getLaunchIntentForPackage(app.packageName);
            if (appLauncher != null) {
                launchableAppMap.put(app.loadLabel(pm).toString(), appLauncher);
            }
        }
        return launchableAppMap;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}