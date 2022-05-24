package com.example.studentdatabaseroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentdatabaseroom.adapters.StudentListAdapter;
import com.example.studentdatabaseroom.data.Group;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.StudentRepository;
import com.example.studentdatabaseroom.viewmodels.FiltrationStudentsViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupListViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FiltrationStudentsViewModel viewModel = getViewModel();

        AutoCompleteTextView groupNameFilter = view.findViewById(R.id.filter_group_name);

        groupNameFilter.setOnItemClickListener((adap, v, a, b) -> {
            Group selected = (Group) adap.getAdapter().getItem(a);
            viewModel.setGroupFilter(selected);
            viewModel.updateStudents();
        });
        groupNameFilter.setThreshold(1);

        viewModel.getGroups().observe(getViewLifecycleOwner(), groups -> {
            ArrayAdapter<Group> groupAdapter = null;

            if (groups != null) {

                ArrayList<Group> list = new ArrayList<Group>() {
                    {
                        addAll(groups);
                    }
                };

                groupAdapter = new ArrayAdapter<Group>(getContext(), android.R.layout.select_dialog_item, list);
            }
            groupNameFilter.setAdapter(groupAdapter);
        });
        EditText studentSurnameText = view.findViewById(R.id.filter_student_surname);
        studentSurnameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String surname = editable.toString();
                viewModel.setSurnameFilter(surname);
                viewModel.updateStudents();
            }
        });

        StudentListAdapter studentsAdapter = new StudentListAdapter();
        studentsAdapter.itemClickListener = student -> {
            if (student != null) {

            }
        };

        RecyclerView studentsRecyclerView = view.findViewById(R.id.students_recycler_view);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentsRecyclerView.setAdapter(studentsAdapter);

        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            studentsAdapter.submitList(students);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.updateGroups();
        viewModel.updateStudents();
    }

    private FiltrationStudentsViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        GroupRepository groupRepository = app.getGroupRepository();
        StudentRepository studentRepository = app.getStudentRepository();
        FiltrationStudentsViewModel.FiltrationStudentsViewModelFactory factory = new FiltrationStudentsViewModel.FiltrationStudentsViewModelFactory(studentRepository ,groupRepository);
        FiltrationStudentsViewModel viewModel = new ViewModelProvider(this, factory).get(FiltrationStudentsViewModel.class);
        return  viewModel;
    }
}