package com.min.im.views;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import java.util.List;


public class appListViews extends Fragment {
    private AppListAdapter adapter;
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
        adapter = new AppListAdapter(getContext(), getAllApps());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<String> getAllApps() {
        List<String> launchableAppList = new ArrayList<>();
        PackageManager pm = requireContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfos) {
            String appName = resolveInfo.loadLabel(pm).toString();
            String packageName = resolveInfo.activityInfo.packageName;

            if (!(packageName.contains("com.android") || packageName.contains("com.google"))) {
                launchableAppList.add(appName);
            }
        }

        return launchableAppList;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}