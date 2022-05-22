package com.example.studentdatabaseroom.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentdatabaseroom.R;
import com.example.studentdatabaseroom.data.Group;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);

        this.groupName = itemView.findViewById(R.id.text_faculty_name);
        this.groupNumber = itemView.findViewById(R.id.text_group_number);
    }

    private final TextView groupName;
    private final TextView groupNumber;

    public void bind(Group group) {
        groupName.setText(group.getFacultyName());

        String numberValue = "" + group.getNumber();
        groupNumber.setText(numberValue);
    }
}
