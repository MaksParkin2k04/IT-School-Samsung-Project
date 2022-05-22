package com.example.studentdatabaseroom.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.studentdatabaseroom.R;
import com.example.studentdatabaseroom.data.Student;

public class StudentListAdapter extends ListAdapter<Student, StudentViewHolder> {
    public StudentListAdapter() {
        super(new StudentDiff());
    }

    public StudentItemClickListener itemClickListener = (student) -> { };

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = this.getItem(position);
        holder.bind(student);
        holder.itemView.setOnClickListener((view) -> {
            itemClickListener.onClick(student);
        });
    }

    static class StudentDiff extends DiffUtil.ItemCallback<Student> {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId() == (newItem.getId());
        }
    }
}