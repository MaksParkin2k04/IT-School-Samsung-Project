package com.example.studentdatabaseroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentdatabaseroom.adapters.StudentListAdapter;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.viewmodels.AddGroupViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsGroupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long groupId = DetailsGroupFragmentArgs.fromBundle(getArguments()).getGroupId();

        GroupDetailsViewModel viewModel = getViewModel();

        StudentListAdapter adapter = new StudentListAdapter();
        adapter.itemClickListener = student -> {
            if (student != null) {
                NavDirections navDirections = DetailsGroupFragmentDirections.actionDetailsGroupToEditStudent(student.getId());
                Navigation.findNavController(view).navigate(navDirections);
            }
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton addButton = view.findViewById(R.id.floating_button_student_add);
        addButton.setOnClickListener((button) -> {
            NavDirections navDirections = DetailsGroupFragmentDirections.actionDetailsGroupToAddStudent(groupId);
            Navigation.findNavController(view).navigate(navDirections);
        });

        viewModel.getTitle().observe(this, title -> {
            if (title != null) {

            }
        });

        viewModel.getStudents().observe(this, students -> {
            adapter.submitList(students);
        });



        viewModel.selectGroupWithStudents(groupId);
    }


    private GroupDetailsViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        GroupRepository groupRepository = app.getGroupRepository();
        GroupDetailsViewModel.GroupDetailsViewModelFactory factory = new GroupDetailsViewModel.GroupDetailsViewModelFactory(groupRepository);
        GroupDetailsViewModel viewModel = new ViewModelProvider(this, factory).get(GroupDetailsViewModel.class);
        return viewModel;
    }
}