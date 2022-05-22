package com.example.studentdatabaseroom;

import android.app.Application;

import com.example.studentdatabaseroom.data.AppDatabase;
import com.example.studentdatabaseroom.data.GroupRepository;
import com.example.studentdatabaseroom.data.StudentRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private static final ExecutorService executorService  = Executors.newFixedThreadPool(4);

    private AppDatabase appDatabase = null;

    private GroupRepository groupRepository = null;
    private StudentRepository studentRepository = null;


    private AppDatabase getAppDatabase() {
        if (appDatabase == null) {
            appDatabase = AppDatabase.create(this);
        }
        return appDatabase;
    }

    public GroupRepository getGroupRepository() {
        if (groupRepository == null) {
            AppDatabase database = getAppDatabase();
            groupRepository = new GroupRepository(executorService, database.getGroupDao());
        }
        return groupRepository;
    }

    public StudentRepository getStudentRepository() {
        if (studentRepository == null) {
            AppDatabase database = getAppDatabase();
            studentRepository = new StudentRepository(executorService, database.getStudentDao());
        }
        return studentRepository;
    }
}
