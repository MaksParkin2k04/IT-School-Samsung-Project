package com.example.studentdatabaseroom.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentdatabaseroom.R;
import com.example.studentdatabaseroom.data.Student;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        this.studentName = itemView.findViewById(R.id.student_list_item_name);
    }

    private final TextView studentName;

    public void bind(Student student) {
        StringBuilder builder = new StringBuilder();

        if (student.getSurname() != null && student.getSurname().length() > 0) {
            builder.append(student.getSurname());
            builder.append(' ');
        }

        if (student.getName() != null && student.getName().length() > 0) {
            builder.append(student.getName());
            builder.append(' ');
        }

        if (student.getPatronymic() != null && student.getPatronymic().length() > 0) {
            builder.append(student.getPatronymic());
        }

        studentName.setText(builder.toString());
    }
}
