package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.Student;
import com.example.studentdatabaseroom.data.StudentRepository;
import com.example.studentdatabaseroom.data.Result;

import java.util.Date;

public class EditStudentViewModel extends BaseViewModel {
    public EditStudentViewModel(@NonNull StudentRepository repository) {
        this.repository = repository;
    }

    private final StudentRepository repository;

    private final MutableLiveData<Student> currentStudent = new MutableLiveData<>(null);

    public LiveData<Student> getCurrentStudent() {
        return currentStudent;
    }

    public void getStudent(long studentId) {
        repository.getStudent(studentId, result -> {
            if (result instanceof Result.Success) {
                Student student = ((Result.Success<Student>) result).data;
                currentStudent.postValue(student);
            } else {
                Exception exception = ((Result.Error<Student>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public void update(String name, String patronymic, String surname, Date dateBirth) {
        Student currentStudent = getCurrentStudent().getValue();
        if (currentStudent != null) {
            currentStudent.setName(name);
            currentStudent.setPatronymic(patronymic);
            currentStudent.setSurname(surname);
            currentStudent.setDateBirth(dateBirth);

            repository.update(currentStudent, result -> {
                if (result instanceof Result.Success) {
                    int updateCount = ((Result.Success<Integer>) result).data;
                    operationCompleted.postValue(true);
                } else {
                    Exception exception = ((Result.Error<Integer>) result).exception;
                    error.postValue(exception.getMessage());
                }
            });
        }
    }

    public void delete() {
        Student currentStudent = getCurrentStudent().getValue();

        repository.remove(currentStudent, result -> {
            if (result instanceof Result.Success) {
                int deleteCount = ((Result.Success<Integer>) result).data;
                operationCompleted.postValue(true);
            } else {
                Exception exception = ((Result.Error<Integer>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public static class EditStudentViewModelFactory implements ViewModelProvider.Factory {

        public EditStudentViewModelFactory(@NonNull StudentRepository repository) {
            this.repository = repository;
        }

        private final StudentRepository repository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(EditStudentViewModel.class)) {
                return (T) new EditStudentViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
