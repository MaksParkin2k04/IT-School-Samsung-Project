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

import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.StudentRepository;
import com.example.studentdatabaseroom.viewmodels.EditGroupViewModel;
import com.example.studentdatabaseroom.viewmodels.EditStudentViewModel;
import com.example.studentdatabaseroom.viewmodels.GroupDetailsViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditStudentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_student, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final long studentId = EditStudentFragmentArgs.fromBundle(getArguments()).getStudentId();
        final long groupId = EditStudentFragmentArgs.fromBundle(getArguments()).getGroupId();

        EditStudentViewModel viewModel = getViewModel();

        EditText studentSurname = view.findViewById(R.id.edit_student_surname);
        EditText studentName = view.findViewById(R.id.edit_student_name);
        EditText studentPatronymic = view.findViewById(R.id.edit_student_patronymic);
        DatePicker studentDateBirth = view.findViewById(R.id.edit_student_date_birth);

        Button updateButton = view.findViewById(R.id.edit_student_update_button);
        updateButton.setOnClickListener(buttonView -> {
            String surname = studentSurname.getText().toString();
            String name = studentName.getText().toString();
            String patronymic = studentPatronymic.getText().toString();

            int day = studentDateBirth.getDayOfMonth();
            int month = studentDateBirth.getMonth();
            int year = studentDateBirth.getYear();

            GregorianCalendar calendar = new GregorianCalendar(year, month, day);
            Date dateBirth = calendar.getTime();

            viewModel.update(name, patronymic, surname, dateBirth);
        });

        Button deleteButton = view.findViewById(R.id.edit_student_delete_button);
        deleteButton.setOnClickListener(buttonView -> {
            viewModel.delete();
        });

        viewModel.getCurrentStudent().observe(getViewLifecycleOwner(), currentStudent -> {
            if (currentStudent != null) {
                studentSurname.setText(currentStudent.getSurname());
                studentName.setText(currentStudent.getName());
                studentPatronymic.setText(currentStudent.getPatronymic());

                Date date = currentStudent.getDateBirth();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                studentDateBirth.init(year, month, day, null);
            } else {
                studentSurname.setText(null);
                studentName.setText(null);
                studentPatronymic.setText(null);

                Date date = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                studentDateBirth.init(year, month, day, null);
            }
        });

        viewModel.getOperationCompleted().observe(getViewLifecycleOwner(), result -> {
            if (result != null && result == true) {
                NavDirections navDirections = EditStudentFragmentDirections.actionEditStudentToDetailsGroup(groupId);
                Navigation.findNavController(view).navigate(navDirections);
            }
        });

        viewModel.getStudent(studentId);

    }

    private EditStudentViewModel getViewModel() {
        App app = (App) getActivity().getApplication();
        StudentRepository studentRepository = app.getStudentRepository();
        EditStudentViewModel.EditStudentViewModelFactory factory = new EditStudentViewModel.EditStudentViewModelFactory(studentRepository);
        EditStudentViewModel viewModel = new ViewModelProvider(this, factory).get( EditStudentViewModel.class);
        return  viewModel;
    }
}