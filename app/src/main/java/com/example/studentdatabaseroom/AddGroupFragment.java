package com.example.studentdatabaseroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.viewmodels.AddGroupViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupListViewModel;


public class AddGroupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddGroupViewModel viewModel = getViewModel();

        EditText facultyName = view.findViewById(R.id.add_edit_text_faculty_name);
        EditText groupNumber = view.findViewById(R.id.add_edit_text_group_number);

        Button addButton = view.findViewById(R.id.add_group_add_button);
        addButton.setOnClickListener((v) -> {
            String name = facultyName.getText().toString();
            int number = Integer.parseInt(groupNumber.getText().toString());
            viewModel.add(name, number);
        });

        viewModel.getError().observe(this, message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getOperationCompleted().observe(this, result -> {
            if (result != null && result == true) {
                NavDirections navDirections = AddGroupFragmentDirections.actionAddGroupToViewGroups();
                Navigation.findNavController(view).navigate(navDirections);
            }
        });
    }

    private AddGroupViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        GroupRepository groupRepository = app.getGroupRepository();
        AddGroupViewModel.AddGroupViewModelFactory factory = new AddGroupViewModel.AddGroupViewModelFactory(groupRepository);
        AddGroupViewModel viewModel = new ViewModelProvider(this, factory).get(AddGroupViewModel.class);
        return  viewModel;
    }
}