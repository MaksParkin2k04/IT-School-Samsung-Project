package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.GroupWithStudents;
import com.example.studentdatabaseroom.data.Result;
import com.example.studentdatabaseroom.data.Student;
import com.example.studentdatabaseroom.data.GroupRepository;

import java.util.List;

public class GroupDetailsViewModel extends BaseViewModel {
    public GroupDetailsViewModel(@NonNull GroupRepository repository) {
        this.repository = repository;
    }

    private final GroupRepository repository;
    private final MutableLiveData<List<Student>> students = new MutableLiveData<>(null);
    private final MutableLiveData<String> title = new MutableLiveData<>(null);

    public LiveData<List<Student>> getStudents() {
        return students;
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public void selectGroupWithStudents(long groupId) {
        repository.getGroupWithStudents(groupId, result -> {
            if (result instanceof Result.Success) {
                GroupWithStudents selectGroup = ((Result.Success<GroupWithStudents>) result).data;
                students.postValue(selectGroup.getStudents());
                title.postValue(selectGroup.getFullName());
            } else {
                Exception exception = ((Result.Error<GroupWithStudents>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public static class GroupDetailsViewModelFactory implements ViewModelProvider.Factory {

        public GroupDetailsViewModelFactory(@NonNull GroupRepository repository) {
            this.repository = repository;
        }

        private final GroupRepository repository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(GroupDetailsViewModel.class)) {
                return (T) new GroupDetailsViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
