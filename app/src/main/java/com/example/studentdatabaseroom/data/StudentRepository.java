package com.example.studentdatabaseroom.data;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;

public class StudentRepository {
    public StudentRepository(@NonNull Executor executor, @NonNull StudentDao dao) {
        this.executor = executor;
        this.dao = dao;
    }

    private final Executor executor;
    private final StudentDao dao;

    public void getStudents(final long groupId, final RepositoryCallback<List<Student>> callback) {
        executor.execute(() -> {
            try {
                List<Student> students = dao.selectStudents(groupId);
                Result<List<Student>> result = new Result.Success<>(students);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<List<Student>> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void getStudent(final long studentId, final RepositoryCallback<Student> callback) {
        executor.execute(() -> {
            try {
                Student student = dao.selectStudent(studentId);
                Result<Student> result = new Result.Success<>(student);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Student> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void selectStudents(String surname, Group group, final RepositoryCallback<List<Student>> callback) {
        String filter = surname != null ? surname : "";

        executor.execute(() -> {
            try {
                List<Student> students = null;

                if (group != null) {
                    students = dao.selectStudents(filter + "%", group.getId());
                } else {
                    students = dao.selectStudents(filter + "%");
                }

                Result<List<Student>> result = new Result.Success<>(students);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<List<Student>> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void add(Student student, final RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            try {
                long addStudentId = dao.insert(student);
                Result<Long> result = new Result.Success<>(addStudentId);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Long> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void update(Student student, final RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            try {
                int updateCount = dao.update(student);
                Result<Integer> result = new Result.Success<>(updateCount);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Integer> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void remove(Student student, final RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            try {
                int deleteCount = dao.delete(student);
                Result<Integer> result = new Result.Success<>(deleteCount);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Integer> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }
}
