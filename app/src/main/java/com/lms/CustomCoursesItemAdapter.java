package com.lms;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.lms.R;

import java.util.ArrayList;
import java.util.Map;

public class CustomCoursesItemAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<Map<String, String>> courses = new ArrayList<>();

    LayoutInflater inflater;
    String currentUser;

    public CustomCoursesItemAdapter(Context context, ArrayList<Map<String, String>> courses, String currentUser)
    {
        this.context = context;
        this.courses = courses;
        this.currentUser = currentUser;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return courses.size();
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
        Map<String, String> course = courses.get(i);

        view = inflater.inflate(R.layout.custom_courses_item,null);
        ConstraintLayout clCourseItem = view.findViewById(R.id.clCourseItem);
        TextView tvCourseName = view.findViewById(R.id.tvCourseName);
        TextView tvCourseDescription = view.findViewById(R.id.tvCourseDescription);

        tvCourseName.setText(course.get("courseName"));
        tvCourseDescription.setText(course.get("courseDescription"));

        clCourseItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, CoursePage.class);
            intent.putExtra("courseId", course.get("id"));
            intent.putExtra("currentUser", currentUser);
            startActivity(context, intent, null);
        });
        return view;
    }
}