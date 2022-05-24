package com.example.studentdatabaseroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentdatabaseroom.adapters.StudentListAdapter;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.viewmodels.AddGroupViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsGroupFragment extends Fragment {

    private long groupId;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupId = DetailsGroupFragmentArgs.fromBundle(getArguments()).getGroupId();

        navController = Navigation.findNavController(view);

        GroupDetailsViewModel viewModel = getViewModel();

        StudentListAdapter adapter = new StudentListAdapter();
        adapter.itemClickListener = student -> {
            if (student != null) {
                NavDirections navDirections = DetailsGroupFragmentDirections.actionDetailsGroupToEditStudent(student.getId(), groupId);
                navController.navigate(navDirections);
            }
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton addButton = view.findViewById(R.id.floating_button_student_add);
        addButton.setOnClickListener((button) -> {
            NavDirections navDirections = DetailsGroupFragmentDirections.actionDetailsGroupToAddStudent(groupId);
            navController.navigate(navDirections);
        });

        viewModel.getTitle().observe(getViewLifecycleOwner(), title -> {
            if (title != null) {

            }
        });

        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            adapter.submitList(students);
        });

        viewModel.selectGroupWithStudents(groupId);
    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_group, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_group_edit) {
            NavDirections navDirections = DetailsGroupFragmentDirections.actionDetailsGroupToEditGroup(groupId);
            navController.navigate(navDirections);
        }

        return super.onOptionsItemSelected(item);
    }


    private GroupDetailsViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        GroupRepository groupRepository = app.getGroupRepository();
        GroupDetailsViewModel.GroupDetailsViewModelFactory factory = new GroupDetailsViewModel.GroupDetailsViewModelFactory(groupRepository);
        GroupDetailsViewModel viewModel = new ViewModelProvider(this, factory).get(GroupDetailsViewModel.class);
        return viewModel;
    }
}