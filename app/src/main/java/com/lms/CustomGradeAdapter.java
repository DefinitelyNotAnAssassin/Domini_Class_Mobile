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

public class CustomGradeAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Map<String, String>> grades = new ArrayList<>();

    LayoutInflater inflater;
    String currentUser;

    public CustomGradeAdapter(Context context, ArrayList<Map<String, String>> grades, String currentUser)
    {
        this.currentUser = currentUser;
        this.context = context;
        this.grades = grades;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return grades.size();
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

        view = inflater.inflate(R.layout.custom_grades,null);
        Map<String, String> currentLesson = grades.get(i);

        TextView tvCourseCodeCustomGrades = view.findViewById(R.id.tvCourseCodeCustomGrades);
        TextView tvGradeCustomGrades = view.findViewById(R.id.tvGradeCustomGrades);
        TextView tvCourseNameCustomGrades = view.findViewById(R.id.tvCourseNameCustomGrades);

        tvCourseCodeCustomGrades.setText(currentLesson.get("course"));
        tvGradeCustomGrades.setText(currentLesson.get("grade"));
        tvCourseNameCustomGrades.setText(currentLesson.get("courseName"));
        return view;
    }
}