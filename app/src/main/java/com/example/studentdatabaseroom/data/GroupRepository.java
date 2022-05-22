package com.example.studentdatabaseroom.data;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class GroupRepository {
    public GroupRepository(@NonNull Executor executor, @NonNull GroupDao dao) {
        this.executor = executor;
        this.dao = dao;
    }

    private final Executor executor;
    private final GroupDao dao;

    public void getGroups(final RepositoryCallback<List<Group>> callback) {
        executor.execute(() -> {
            try {
                List<Group> groups = dao.selectGroups();
                Result<List<Group>> result = new Result.Success<>(groups);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<List<Group>> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void getGroup(final long groupId, final RepositoryCallback<Group> callback) {
        executor.execute(() -> {
            try {
                Group group = dao.selectGroup(groupId);
                Result<Group> result = new Result.Success<>(group);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Group> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void getGroupWithStudents(final long groupId, final RepositoryCallback<GroupWithStudents> callback) {
        executor.execute(() -> {
            try {
                GroupWithStudents group = dao.selectGroupWithStudents(groupId);

                // Сортировка по фамилии
                Collections.sort(group.getStudents(), (a, b) -> {
                    if (a != null && b != null) {
                        return a.getSurname().compareTo(b.getSurname());
                    }
                    return 0;
                });

                Result<GroupWithStudents> result = new Result.Success<>(group);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<GroupWithStudents> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void add(Group group, final RepositoryCallback<Long> callback) {
        executor.execute(() -> {
            try {
                long addGroupId = dao.insert(group);
                Result<Long> result = new Result.Success<>(addGroupId);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Long> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void update(Group group, final RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            try {
                int updateCount = dao.update(group);
                Result<Integer> result = new Result.Success<>(updateCount);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Integer> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }

    public void remove(Group group, final RepositoryCallback<Integer> callback) {
        executor.execute(() -> {
            try {
                int deleteCount = dao.delete(group);
                Result<Integer> result = new Result.Success<>(deleteCount);
                callback.onComplete(result);
            } catch (Exception error) {
                Result<Integer> result = new Result.Error<>(error);
                callback.onComplete(result);
            }
        });
    }
}
