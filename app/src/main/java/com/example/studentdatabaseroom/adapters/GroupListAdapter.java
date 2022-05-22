package com.example.studentdatabaseroom.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.studentdatabaseroom.R;
import com.example.studentdatabaseroom.data.Group;

public class GroupListAdapter extends ListAdapter<Group, GroupViewHolder> {
    public GroupListAdapter() {
        super(new GroupDiff());
    }

    public GroupItemClickListener itemClickListener = (group) -> { };

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = this.getItem(position);
        holder.bind(group);
        holder.itemView.setOnClickListener((view) -> {
            itemClickListener.onClick(group);
        });
    }

    static class GroupDiff extends DiffUtil.ItemCallback<Group> {
        @Override
        public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getId() == (newItem.getId());
        }
    }
}
