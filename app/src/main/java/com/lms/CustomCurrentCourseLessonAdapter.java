package com.lms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class CustomCurrentCourseLessonAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Map<String, String>> lessons = new ArrayList<>();

    LayoutInflater inflater;


    public CustomCurrentCourseLessonAdapter(Context context, ArrayList<Map<String, String>> lessons)
    {
        this.context = context;
        this.lessons = lessons;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return lessons.size();
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

        view = inflater.inflate(R.layout.custom_currentcourse_lesson,null);
        Map<String, String> currentLesson = lessons.get(i);

        TextView tvCurrentCourseLessonTitle = view.findViewById(R.id.tvCurrentCourseLessonTitle);
        tvCurrentCourseLessonTitle.setText(currentLesson.get("name"));

        tvCurrentCourseLessonTitle.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityPage.class);
            intent.putExtra("lessonId", currentLesson.get("id"));
            context.startActivity(intent);
        });
        return view;
    }
}