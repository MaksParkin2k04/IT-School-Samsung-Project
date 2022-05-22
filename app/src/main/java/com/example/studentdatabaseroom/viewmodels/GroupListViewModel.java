package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.Group;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.Result;

import java.util.List;

public class GroupListViewModel extends BaseViewModel {

    public GroupListViewModel(@NonNull GroupRepository repository) {
        this.repository = repository;
    }

    private final GroupRepository repository;
    private final MutableLiveData<List<Group>> groups = new MutableLiveData<>(null);

    public LiveData<List<Group>> getGroups() {
        return groups;
    }

    public void selectGroups() {
        repository.getGroups(result -> {
            if (result instanceof Result.Success) {
                List<Group> group = ((Result.Success<List<Group>>) result).data;
                groups.postValue(group);
            } else {
                Exception exception = ((Result.Error<List<Group>>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public static class GroupListViewModelFactory implements ViewModelProvider.Factory {

        public GroupListViewModelFactory(@NonNull GroupRepository repository) {
            this.repository = repository;
        }

        private final GroupRepository repository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(GroupListViewModel.class)) {
                return (T) new GroupListViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
