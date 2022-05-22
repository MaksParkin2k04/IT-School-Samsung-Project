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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.StudentRepository;
import com.example.studentdatabaseroom.viewmodels.AddStudentViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupListViewModel;

import java.util.Date;
import java.util.GregorianCalendar;

public class AddStudentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_student, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long groupId = AddStudentFragmentArgs.fromBundle(getArguments()).getGroupId();

        AddStudentViewModel viewModel = getViewModel();

        EditText studentSurname = view.findViewById(R.id.add_student_surname);
        EditText studentName = view.findViewById(R.id.add_student_name);
        EditText studentPatronymic = view.findViewById(R.id.add_student_patronymic);

        DatePicker studentDateBirth = view.findViewById(R.id.add_student_date_birth);
        Button addButton = view.findViewById(R.id.add_student_add_button);
        addButton.setOnClickListener(buttonView -> {
            String surname = studentSurname.getText().toString();
            String name = studentName.getText().toString();
            String patronymic = studentPatronymic.getText().toString();

            int day = studentDateBirth.getDayOfMonth();
            int month = studentDateBirth.getMonth();
            int year = studentDateBirth.getYear();

            GregorianCalendar calendar = new GregorianCalendar(year, month, day);
            Date dateBirth = calendar.getTime();

            viewModel.add(name, patronymic, surname, dateBirth, groupId);
        });

        viewModel.getError().observe(this, message -> {
            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getOperationCompleted().observe(this, result -> {
            if (result != null && result == true) {
                NavDirections navDirections = AddStudentFragmentDirections.actionAddStudentToDetailsGroup(groupId);
                Navigation.findNavController(view).navigate(navDirections);
            }
        });
    }

    private AddStudentViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        StudentRepository groupRepository = app.getStudentRepository();
        AddStudentViewModel.AddStudentViewModelFactory factory = new AddStudentViewModel.AddStudentViewModelFactory(groupRepository);
        AddStudentViewModel viewModel = new ViewModelProvider(this, factory).get(AddStudentViewModel.class);
        return viewModel;
    }
}