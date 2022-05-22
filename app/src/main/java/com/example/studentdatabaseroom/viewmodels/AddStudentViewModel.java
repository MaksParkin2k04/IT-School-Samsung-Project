package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.Student;
import com.example.studentdatabaseroom.data.StudentRepository;
import com.example.studentdatabaseroom.data.Result;

import java.util.Date;

public class AddStudentViewModel extends BaseViewModel {
    public AddStudentViewModel(@NonNull StudentRepository repository) {
        this.repository = repository;
    }

    private final StudentRepository repository;

    public void add(String name, String patronymic, String surname, Date dateBirth, long groupId) {
        Student student = new Student(0, name, patronymic, surname, dateBirth, groupId);

        repository.add(student, result -> {
            if (result instanceof Result.Success) {
                long addStudentId = ((Result.Success<Long>) result).data;
                operationCompleted.postValue(true);
            } else {
                Exception exception = ((Result.Error<Long>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public static class AddStudentViewModelFactory implements ViewModelProvider.Factory {

        public AddStudentViewModelFactory(@NonNull StudentRepository repository) {
            this.repository = repository;
        }

        private final StudentRepository repository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AddStudentViewModel.class)) {
                return (T) new AddStudentViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
