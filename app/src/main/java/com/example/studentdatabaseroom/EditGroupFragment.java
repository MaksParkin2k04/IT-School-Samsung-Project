package com.example.studentdatabaseroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.viewmodels.EditGroupViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupListViewModel;

public class EditGroupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long groupId = EditGroupFragmentArgs.fromBundle(getArguments()).getGroupId();

        EditGroupViewModel viewModel = getViewModel();

        EditText facultyName = view.findViewById(R.id.edit_group_faculty_name);
        EditText groupNumber = view.findViewById(R.id.edit_group_group_number);

        Button updateButton = view.findViewById(R.id.edit_group_update_button);
        Button deleteButton = view.findViewById(R.id.edit_group_delete_button);

        updateButton.setOnClickListener(buttonView -> {
            String name = facultyName.getText().toString();
            int number = Integer.parseInt(groupNumber.getText().toString()); // ?

            viewModel.update(name, number);
        });

        deleteButton.setOnClickListener(buttonView -> {
            viewModel.delete();
        });

        viewModel.getCurrentGroup().observe(getViewLifecycleOwner(), currentGroup -> {
            if (currentGroup != null) {
                facultyName.setText(currentGroup.getFacultyName());
                groupNumber.setText(String.valueOf(currentGroup.getNumber()));
            } else {
                facultyName.setText(null);
                groupNumber.setText(null);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getOperationCompleted().observe(getViewLifecycleOwner(), result -> {
            if (result != null && result == true) {
                NavDirections navDirections = EditGroupFragmentDirections.actionEditGroupToDetailsGroup(groupId);
                Navigation.findNavController(view).navigate(navDirections);
            }
        });

        viewModel.getRemoveOperationCompleted().observe(getViewLifecycleOwner(), result -> {
            if (result != null && result == true) {
                NavDirections navDirections = EditGroupFragmentDirections.actionEditGroupToViewGroups();
                Navigation.findNavController(view).navigate(navDirections);
            }
        });

        viewModel.getGroup(groupId);
    }

    private EditGroupViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        GroupRepository groupRepository = app.getGroupRepository();
        EditGroupViewModel.EditGroupViewModelFactory factory = new EditGroupViewModel.EditGroupViewModelFactory(groupRepository);
        EditGroupViewModel viewModel = new ViewModelProvider(this, factory).get(EditGroupViewModel.class);
        return  viewModel;
    }

}