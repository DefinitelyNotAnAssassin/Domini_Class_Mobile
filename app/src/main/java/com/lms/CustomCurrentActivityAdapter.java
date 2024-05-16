package com.lms;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class CustomCurrentActivityAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Map<String, String>> activity = new ArrayList<>();

    LayoutInflater inflater;


    public CustomCurrentActivityAdapter(Context context, ArrayList<Map<String, String>> activity)
    {
        this.context = context;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return activity.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.custom_currentcourse_activity,null);
        Map<String, String> currentLesson = activity.get(i);

        TextView tvCurrentCourseActivityTitle = view.findViewById(R.id.tvCurrentCourseActivityTitle);
        tvCurrentCourseActivityTitle.setText(currentLesson.get("activityName"));
        return view;
    }
}