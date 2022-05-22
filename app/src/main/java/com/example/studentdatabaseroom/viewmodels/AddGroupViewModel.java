package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.Group;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.Result;

public class AddGroupViewModel extends BaseViewModel {
    public AddGroupViewModel(@NonNull GroupRepository repository) {
        this.repository = repository;
    }

    private final GroupRepository repository;

    public void add(String facultyName, int groupNumber) {
        Group group = new Group(0, groupNumber, facultyName);

        repository.add(group, result -> {
            if (result instanceof Result.Success) {
                long addGroupId = ((Result.Success<Long>) result).data;
                operationCompleted.postValue(true);
            } else {
                Exception exception = ((Result.Error<Long>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public static class AddGroupViewModelFactory implements ViewModelProvider.Factory {

        public AddGroupViewModelFactory(@NonNull GroupRepository repository) {
            this.repository = repository;
        }

        private final GroupRepository repository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AddGroupViewModel.class)) {
                return (T) new AddGroupViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
