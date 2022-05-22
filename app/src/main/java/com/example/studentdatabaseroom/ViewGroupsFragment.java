package com.example.studentdatabaseroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.studentdatabaseroom.adapters.GroupListAdapter;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.viewmodels.GroupListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewGroupsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GroupListViewModel viewModel = getViewModel();

        GroupListAdapter adapter = new GroupListAdapter();
        adapter.itemClickListener = group -> {
            if (group != null) {
                long groupId = group.getId();
                NavDirections navDirections = ViewGroupsFragmentDirections.actionViewGroupsToDetailsGroup(groupId);
                Navigation.findNavController(view).navigate(navDirections);
            }
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton addButton = view.findViewById(R.id.floating_button_group_add);
        addButton.setOnClickListener((button) -> {
           NavDirections navDirections = ViewGroupsFragmentDirections.actionViewGroupsToAddGroup();
            Navigation.findNavController(view).navigate(navDirections);
        });

        viewModel.getGroups().observe(this, groups -> {
            adapter.submitList(groups);
        });

        viewModel.getError().observe(this, message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.selectGroups();
    }


    private GroupListViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        GroupRepository groupRepository = app.getGroupRepository();
        GroupListViewModel.GroupListViewModelFactory factory = new GroupListViewModel.GroupListViewModelFactory(groupRepository);
        GroupListViewModel viewModel = new ViewModelProvider(this, factory).get(GroupListViewModel.class);
        return  viewModel;
    }
}