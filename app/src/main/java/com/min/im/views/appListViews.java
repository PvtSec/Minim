package com.min.im.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.min.im.R;
import androidx.fragment.app.Fragment;

public class appListViews extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.applist_views, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}