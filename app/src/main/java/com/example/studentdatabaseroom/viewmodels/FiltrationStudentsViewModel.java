package com.example.studentdatabaseroom.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.studentdatabaseroom.data.Group;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.GroupWithStudents;
import com.example.studentdatabaseroom.data.Result;
import com.example.studentdatabaseroom.data.Student;
import com.example.studentdatabaseroom.data.StudentRepository;

import java.util.List;

public class FiltrationStudentsViewModel extends BaseViewModel {

    public FiltrationStudentsViewModel(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private Group groupFilter;

    private String surnameFilter;
    private final MutableLiveData<List<Group>> groups = new MutableLiveData<>(null);
    private final MutableLiveData<List<Student>> students = new MutableLiveData<>(null);

    public LiveData<List<Group>> getGroups() {
        return groups;
    }

    public LiveData<List<Student>> getStudents() {
        return students;
    }

    public void setSurnameFilter(String surnameFilter) {
        this.surnameFilter = surnameFilter;
    }

    public void setGroupFilter(Group groupFilter) {
        this.groupFilter = groupFilter;
    }

    public void updateStudents() {
        updateStudents(surnameFilter);
    }

    public void updateGroups() {
        groupRepository.getGroups(result -> {
            if (result instanceof Result.Success) {
                List<Group> group = ((Result.Success<List<Group>>) result).data;
                groups.postValue(group);
            } else {
                Exception exception = ((Result.Error<List<Group>>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    private void updateStudents(String surname) {
        studentRepository.selectStudents(surname, groupFilter, result -> {
            if (result instanceof Result.Success) {
                List<Student> selectStudents = ((Result.Success<List<Student>>) result).data;
                students.postValue(selectStudents);
            } else {
                Exception exception = ((Result.Error<List<Student>>) result).exception;
                error.postValue(exception.getMessage());
            }
        });
    }

    public static class FiltrationStudentsViewModelFactory implements ViewModelProvider.Factory {

        public FiltrationStudentsViewModelFactory(@NonNull StudentRepository studentRepository, @NonNull GroupRepository groupRepository) {
            this.studentRepository = studentRepository;
            this.groupRepository = groupRepository;
        }

        private final StudentRepository studentRepository;
        private final GroupRepository groupRepository;

        @NonNull
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(FiltrationStudentsViewModel.class)) {
                return (T) new FiltrationStudentsViewModel(studentRepository, groupRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
