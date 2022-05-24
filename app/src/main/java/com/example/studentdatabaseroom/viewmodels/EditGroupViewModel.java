package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.Group;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.Result;

public class EditGroupViewModel extends BaseViewModel {
    public EditGroupViewModel(@NonNull GroupRepository repository) {
        this.repository = repository;
    }

    private final GroupRepository repository;

    private final MutableLiveData<Group> currentGroup = new MutableLiveData<>(null);
    protected final MutableLiveData<Boolean> removeOperationCompleted = new MutableLiveData<>(false);

    public LiveData<Group> getCurrentGroup() {
        return currentGroup;
    }

    public LiveData<Boolean> getRemoveOperationCompleted() {
        return removeOperationCompleted;
    }

    public void getGroup(long groupId) {
        repository.getGroup(groupId, result -> {
            if (result instanceof Result.Success) {
                Group group = ((Result.Success<Group>) result).data;
                currentGroup.postValue(group);
            } else {
                Exception exception = ((Result.Error<Group>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public void update(String facultyName, int number) {
        Group currentGroup = getCurrentGroup().getValue();
        currentGroup.setFacultyName(facultyName);
        currentGroup.setNumber(number);

        repository.update(currentGroup, result -> {
            if (result instanceof Result.Success) {
                int updateCount = ((Result.Success<Integer>) result).data;
                operationCompleted.postValue(updateCount ==1);
            } else {
                Exception exception = ((Result.Error<Integer>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public void delete() {
        Group currentGroup = getCurrentGroup().getValue();

        repository.remove(currentGroup, result -> {
            if (result instanceof Result.Success) {
                int deleteCount = ((Result.Success<Integer>) result).data;
                removeOperationCompleted.postValue(deleteCount == 1);
            } else {
                Exception exception = ((Result.Error<Integer>) result).exception;
                String message = exception.getMessage();
                if (message.startsWith("FOREIGN KEY constraint failed")) {
                    error.postValue("Нельзя удалить группу содержащую студентов");
                } else {
                    error.postValue(exception.getMessage());
                }
            }
        });
    }

    public static class EditGroupViewModelFactory implements ViewModelProvider.Factory {

        public EditGroupViewModelFactory(@NonNull GroupRepository repository) {
            this.repository = repository;
        }

        private final GroupRepository repository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(EditGroupViewModel.class)) {
                return (T) new EditGroupViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
