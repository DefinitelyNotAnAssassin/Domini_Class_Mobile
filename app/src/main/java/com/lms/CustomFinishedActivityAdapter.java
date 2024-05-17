package com.lms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class CustomFinishedActivityAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Map<String, String>> activities = new ArrayList<>();

    LayoutInflater inflater;
    String currentUser;

    public CustomFinishedActivityAdapter(Context context, ArrayList<Map<String, String>> activities, String currentUser)
    {
        this.currentUser = currentUser;
        this.context = context;
        this.activities = activities;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return activities.size();
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

        view = inflater.inflate(R.layout.custom_finished_activities,null);
        Map<String, String> currentLesson = activities.get(i);

        TextView tvFinishedActivityTitle = view.findViewById(R.id.tvFinishedActivityTitle);
        TextView tvFinishedIdCourse = view.findViewById(R.id.tvFinishedIdCourse);

        tvFinishedActivityTitle.setText(currentLesson.get("activityName"));
        tvFinishedIdCourse.setText(currentLesson.get("courseName"));

        return view;
    }
}